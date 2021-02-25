package com.ddf.boot.quick.controller.features;

import com.ddf.boot.common.core.exception200.GlobalCallbackCode;
import com.ddf.boot.common.redis.helper.RedisTemplateHelper;
import com.google.common.base.Preconditions;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redisson相关$
 *
 * @author dongfang.ding
 * @date 2020/12/18 0018 23:18
 */
@RequestMapping("redisson")
@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RedissonController {

    private final RedisTemplateHelper redisTemplateHelper;

    private final RedissonClient redissonClient;

    private static RRateLimiter limiter;

    @PostConstruct
    public void init() {
        limiter = redissonClient.getRateLimiter("myLimiter");
        limiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.SECONDS);
    }



    /**
     * 演示基于漏桶算法的分布式限流
     *
     * @return
     */
    @PostMapping("testLeakyBucketRateLimitA")
    public Boolean testLeakyBucketRateLimitA() {
        //        Preconditions.checkArgument(redisTemplateHelper.leakyBucketRateLimitAcquire(RedisRequestDefinition.testLeakyBucketRateLimitA),
        //                GlobalCallbackCode.RATE_LIMIT);
        Preconditions.checkArgument(limiter.tryAcquire(1), GlobalCallbackCode.RATE_LIMIT);
        return Boolean.TRUE;
    }

}
