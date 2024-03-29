package com.ddf.boot.quickstart.core.client;

import com.ddf.boot.common.redis.request.LeakyBucketRateLimitRequest;
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



    /**
     * 演示基于redis的限流测试A
     */
    public static RateLimitRequest testRateLimitA = RateLimitRequest.builder().key("testA").max(20).rate(10).build();

    /**
     * 演示基于redis的限流测试B
     */
    public static RateLimitRequest testRateLimitB = RateLimitRequest.builder().key("testB").max(10).rate(5).build();


    /**
     * 演示基于漏桶算法的限流
     */
    public static LeakyBucketRateLimitRequest testLeakyBucketRateLimitA = LeakyBucketRateLimitRequest.builder().key(
            "testLeakyBucketRateLimitA").rate(5).rateIntervalSeconds(1).permits(1).build();

}
