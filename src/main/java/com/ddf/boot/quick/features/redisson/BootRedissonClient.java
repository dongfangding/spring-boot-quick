package com.ddf.boot.quick.features.redisson;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2020/12/17 0017 23:16
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BootRedissonClient {

    private final RedissonClient redissonClient;
    public RBucket<BucketAble> bucket;

    public void pubBucket(BucketAble bucketAble) {
        bucket = redissonClient.getBucket("com.ddf.boot.quick.features.redisson.BucketAble");
        bucket.set(bucketAble);
    }

    public BucketAble getBucket() {
        return bucket.get();
    }

}
