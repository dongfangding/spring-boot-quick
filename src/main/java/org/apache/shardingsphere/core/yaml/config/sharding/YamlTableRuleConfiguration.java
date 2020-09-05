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

package org.apache.shardingsphere.core.yaml.config.sharding;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.common.yaml.config.YamlConfiguration;

/**
 * Table rule configuration for YAML.
 */
@Getter
@Setter
public final class YamlTableRuleConfiguration implements YamlConfiguration {

    private String logicTable;

    private String actualDataNodes;

    private YamlShardingStrategyConfiguration databaseStrategy;

    private YamlShardingStrategyConfiguration tableStrategy;

    private YamlKeyGeneratorConfiguration keyGenerator;

    /**
     * added by dongFang.Ding 2020-09-05
     * 如果配置了读写分离当前逻辑表是否使用读写分离配置的master数据源
     */
    private boolean userMasterSlaveRulesDatasourceNameIfExist = true;
}
