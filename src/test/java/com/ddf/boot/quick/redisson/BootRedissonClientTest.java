package com.ddf.boot.quick.redisson;

import com.ddf.boot.quick.QuickApplicationTest;
import org.junit.Test;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2020/12/17 0017 23:24
 */
public class BootRedissonClientTest extends QuickApplicationTest {

    @Autowired
    private BootRedissonClient bootRedissonClient;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testPutBucket() {
        RedissonAble redissonAble = new RedissonAble("redissonAble测试", "demo", 1);
        bootRedissonClient.pubBucket(redissonAble);
        System.out.println(bootRedissonClient.getBucket());

        RIdGenerator generator = redissonClient.getIdGenerator("generator");
        generator.tryInit(1, 2);
        System.out.println(generator.nextId());
        System.out.println(generator.nextId());
        System.out.println(generator.nextId());

    }
}
