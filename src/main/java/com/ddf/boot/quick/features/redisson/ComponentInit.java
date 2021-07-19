package com.ddf.boot.quick.features.redisson;

import com.ddf.boot.common.redis.ext.RedisBloomFilter;
import com.ddf.boot.common.redis.ext.RedisTopic;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/07/19 15:42
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ComponentInit {

    private final RedissonClient redissonClient;


    /**
     * 验证唯一名称是否不存在
     *
     * @return
     */
    @Bean(name = "uniqueNameBloomFilter")
    public RedisBloomFilter<String> uniqueNameBloomFilter() {
        return RedisBloomFilter.newInstance("uniqueNameBloomFilter", redissonClient, 200, 0);
    }


    /**
     * 构造topic
     *
     * @return
     */
    @Bean(name = "addUniqueTopic")
    public RedisTopic addUniqueTopic() {
        return RedisTopic.newInstance("addUniqueTopic", redissonClient);
    }
}
