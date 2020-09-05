package com.ddf.boot.quick.sharding;

import cn.hutool.core.collection.CollUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * user_article的数据库分库策略$
 *
 * @author dongfang.ding
 * @date 2020/9/5 0005 13:16
 */
public class UserArticleDatabasePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    /**
     * Sharding.
     *
     * @param availableTargetNames available data sources or tables's names
     * @param shardingValue        sharding value
     * @return sharding result for data source or table's name
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        if (CollUtil.isEmpty(availableTargetNames)) {
            throw new RuntimeException("无可用的数据源");
        }
        // 目前配置了三个数据库,master,slave0,slave1
        // 当前这个分库策略是给user_article用的，分库字段是user_id，userId对可用数据库进行取模
        Long value = shardingValue.getValue();
        long index = value % availableTargetNames.size();

        int i = 0;
        for (String availableTargetName : availableTargetNames) {
            if (i == index) {
                return availableTargetName;
            }
             i ++;
        }
        return null;
    }
}
