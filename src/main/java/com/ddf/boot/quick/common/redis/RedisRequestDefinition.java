package com.ddf.boot.quick.common.redis;

import com.ddf.boot.common.redis.request.RateLimitRequest;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/12/11 11:39
 */
public class RedisRequestDefinition {

    /**
     * oss的限流参数
     */
    public static RateLimitRequest ossRateLimit = RateLimitRequest.builder().key("oss").max(1).rate(1).build();
}
