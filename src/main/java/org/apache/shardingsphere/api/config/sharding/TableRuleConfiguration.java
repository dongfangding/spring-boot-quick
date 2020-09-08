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

package org.apache.shardingsphere.api.config.sharding;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.api.config.sharding.strategy.ShardingStrategyConfiguration;

/**
 * Table rule configuration.
 */
@Getter
@Setter
public final class TableRuleConfiguration {

    private final String logicTable;

    private final String actualDataNodes;

    private ShardingStrategyConfiguration databaseShardingStrategyConfig;

    private ShardingStrategyConfiguration tableShardingStrategyConfig;

    private KeyGeneratorConfiguration keyGeneratorConfig;

    /**
     * added by dongFang.Ding 2020-09-05
     * 如果配置了读写分离当前逻辑表是否使用读写分离配置的master数据源
     */
    private boolean useMasterSlaveRulesDatasourceNameIfExist = true;

    public TableRuleConfiguration(final String logicTable) {
        this(logicTable, null, true);
    }

    public TableRuleConfiguration(final String logicTable, final String actualDataNodes, final boolean useMasterSlaveRulesDatasourceNameIfExist) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(logicTable), "LogicTable is required.");
        this.logicTable = logicTable;
        this.actualDataNodes = actualDataNodes;
        this.useMasterSlaveRulesDatasourceNameIfExist = useMasterSlaveRulesDatasourceNameIfExist;
    }
}
