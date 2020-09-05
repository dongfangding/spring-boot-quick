/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.rule;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.core.strategy.route.ShardingStrategy;
import org.apache.shardingsphere.core.strategy.route.ShardingStrategyFactory;
import org.apache.shardingsphere.core.strategy.route.none.NoneShardingStrategy;
import org.apache.shardingsphere.spi.algorithm.keygen.ShardingKeyGeneratorServiceLoader;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;
import org.apache.shardingsphere.underlying.common.config.exception.ShardingSphereConfigurationException;
import org.apache.shardingsphere.underlying.common.config.inline.InlineExpressionParser;
import org.apache.shardingsphere.underlying.common.exception.ShardingSphereException;
import org.apache.shardingsphere.underlying.common.rule.DataNode;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Table rule.
 */
@Getter
@ToString(exclude = {"dataNodeIndexMap", "actualTables", "actualDatasourceNames", "datasourceToTablesMap"})
public final class TableRule {

    private final String logicTable;

    private final List<DataNode> actualDataNodes;

    @Getter(AccessLevel.NONE)
    private final Set<String> actualTables;

    @Getter(AccessLevel.NONE)
    private final Map<DataNode, Integer> dataNodeIndexMap;

    private final ShardingStrategy databaseShardingStrategy;

    private final ShardingStrategy tableShardingStrategy;

    @Getter(AccessLevel.NONE)
    private final String generateKeyColumn;

    private final ShardingKeyGenerator shardingKeyGenerator;

    private final Collection<String> actualDatasourceNames = new LinkedHashSet<>();

    private final Map<String, Collection<String>> datasourceToTablesMap = new HashMap<>();

    public TableRule(final String defaultDataSourceName, final String logicTableName) {
        logicTable = logicTableName.toLowerCase();
        actualDataNodes = Collections.singletonList(new DataNode(defaultDataSourceName, logicTableName));
        actualTables = getActualTables();
        cacheActualDatasourcesAndTables();
        dataNodeIndexMap = Collections.emptyMap();
        databaseShardingStrategy = null;
        tableShardingStrategy = null;
        generateKeyColumn = null;
        shardingKeyGenerator = null;
    }

    public TableRule(final Collection<String> dataSourceNames, final String logicTableName) {
        logicTable = logicTableName.toLowerCase();
        dataNodeIndexMap = new HashMap<>(dataSourceNames.size(), 1);
        actualDataNodes = generateDataNodes(logicTableName, dataSourceNames);
        actualTables = getActualTables();
        databaseShardingStrategy = null;
        tableShardingStrategy = null;
        generateKeyColumn = null;
        shardingKeyGenerator = null;
    }

    public TableRule(final TableRuleConfiguration tableRuleConfig, final ShardingDataSourceNames shardingDataSourceNames, final String defaultGenerateKeyColumn) {
        logicTable = tableRuleConfig.getLogicTable().toLowerCase();
        List<String> dataNodes = new InlineExpressionParser(tableRuleConfig.getActualDataNodes()).splitAndEvaluate();
        dataNodeIndexMap = new HashMap<>(dataNodes.size(), 1);
        actualDataNodes = isEmptyDataNodes(dataNodes)
                ? generateDataNodes(tableRuleConfig.getLogicTable(), shardingDataSourceNames.getDataSourceNames()) : generateDataNodes(dataNodes, shardingDataSourceNames.getDataSourceNames());
        actualTables = getActualTables();
        databaseShardingStrategy = null == tableRuleConfig.getDatabaseShardingStrategyConfig() ? null : ShardingStrategyFactory.newInstance(tableRuleConfig.getDatabaseShardingStrategyConfig());
        tableShardingStrategy = null == tableRuleConfig.getTableShardingStrategyConfig() ? null : ShardingStrategyFactory.newInstance(tableRuleConfig.getTableShardingStrategyConfig());
        final KeyGeneratorConfiguration keyGeneratorConfiguration = tableRuleConfig.getKeyGeneratorConfig();
        generateKeyColumn = null != keyGeneratorConfiguration && !Strings.isNullOrEmpty(keyGeneratorConfiguration.getColumn()) ? keyGeneratorConfiguration.getColumn() : defaultGenerateKeyColumn;
        shardingKeyGenerator = containsKeyGeneratorConfiguration(tableRuleConfig)
                ? new ShardingKeyGeneratorServiceLoader().newService(tableRuleConfig.getKeyGeneratorConfig().getType(), tableRuleConfig.getKeyGeneratorConfig().getProperties()) : null;
        checkRule(dataNodes);
    }

    private void cacheActualDatasourcesAndTables() {
        for (DataNode each : actualDataNodes) {
            actualDatasourceNames.add(each.getDataSourceName());
            addActualTable(each.getDataSourceName(), each.getTableName());
        }
    }

    private Set<String> getActualTables() {
        return actualDataNodes.stream().map(DataNode::getTableName).collect(Collectors.toSet());
    }

    private void addActualTable(final String datasourceName, final String tableName) {
        datasourceToTablesMap.computeIfAbsent(datasourceName, k -> new LinkedHashSet<>()).add(tableName);
    }

    private boolean containsKeyGeneratorConfiguration(final TableRuleConfiguration tableRuleConfiguration) {
        return null != tableRuleConfiguration.getKeyGeneratorConfig() && !Strings.isNullOrEmpty(tableRuleConfiguration.getKeyGeneratorConfig().getType());
    }

    private boolean isEmptyDataNodes(final List<String> dataNodes) {
        return null == dataNodes || dataNodes.isEmpty();
    }

    private List<DataNode> generateDataNodes(final String logicTable, final Collection<String> dataSourceNames) {
        List<DataNode> result = new LinkedList<>();
        int index = 0;
        for (String each : dataSourceNames) {
            DataNode dataNode = new DataNode(each, logicTable);
            result.add(dataNode);
            dataNodeIndexMap.put(dataNode, index);
            actualDatasourceNames.add(each);
            addActualTable(dataNode.getDataSourceName(), dataNode.getTableName());
            index++;
        }
        return result;
    }

    private List<DataNode> generateDataNodes(final List<String> actualDataNodes, final Collection<String> dataSourceNames) {
        List<DataNode> result = new LinkedList<>();
        int index = 0;
        for (String each : actualDataNodes) {
            DataNode dataNode = new DataNode(each);
            if (!dataSourceNames.contains(dataNode.getDataSourceName())) {
                throw new ShardingSphereException("Cannot find data source in sharding rule, invalid actual data node is: '%s'", each);
            }
            result.add(dataNode);
            dataNodeIndexMap.put(dataNode, index);
            actualDatasourceNames.add(dataNode.getDataSourceName());
            addActualTable(dataNode.getDataSourceName(), dataNode.getTableName());
            index++;
        }
        return result;
    }

    /**
     * Get data node groups.
     *
     * @return data node groups, key is data source name, value is data nodes belong to this data source
     */
    public Map<String, List<DataNode>> getDataNodeGroups() {
        Map<String, List<DataNode>> result = new LinkedHashMap<>(actualDataNodes.size(), 1);
        for (DataNode each : actualDataNodes) {
            String dataSourceName = each.getDataSourceName();
            if (!result.containsKey(dataSourceName)) {
                result.put(dataSourceName, new LinkedList<>());
            }
            result.get(dataSourceName).add(each);
        }
        return result;
    }

    /**
     * Get actual data source names.
     *
     * @return actual data source names
     */
    public Collection<String> getActualDatasourceNames() {
        return actualDatasourceNames;
    }

    /**
     * Get actual table names via target data source name.
     *
     * @param targetDataSource target data source name
     * @return names of actual tables
     */
    public Collection<String> getActualTableNames(final String targetDataSource) {
        return datasourceToTablesMap.getOrDefault(targetDataSource, Collections.emptySet());
    }

    int findActualTableIndex(final String dataSourceName, final String actualTableName) {
        return dataNodeIndexMap.getOrDefault(new DataNode(dataSourceName, actualTableName), -1);
    }

    boolean isExisted(final String actualTableName) {
        return actualTables.contains(actualTableName);
    }

    private void checkRule(final List<String> dataNodes) {
        if (isEmptyDataNodes(dataNodes) && null != tableShardingStrategy && !(tableShardingStrategy instanceof NoneShardingStrategy)) {
            throw new ShardingSphereConfigurationException("ActualDataNodes must be configured if want to shard tables for logicTable [%s]", logicTable);
        }
    }

    /**
     * Get generate key column.
     *
     * @return generate key column
     */
    public Optional<String> getGenerateKeyColumn() {
        return Optional.ofNullable(generateKeyColumn);
    }
}
