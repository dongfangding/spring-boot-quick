package com.ddf.boot.quick.controller.features;

import com.ddf.boot.common.core.exception200.BusinessException;
import com.ddf.boot.common.core.exception200.GlobalCallbackCode;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.lock.DistributedLock;
import com.ddf.boot.common.lock.exception.LockingAcquireException;
import com.ddf.boot.common.lock.exception.LockingReleaseException;
import com.ddf.boot.common.lock.redis.impl.RedisDistributedLock;
import com.ddf.boot.common.lock.zk.impl.ZookeeperDistributedLock;
import com.ddf.boot.common.redis.ext.RedisBloomFilter;
import com.ddf.boot.common.redis.ext.RedisTopic;
import com.ddf.boot.common.redis.helper.RedisTemplateHelper;
import com.ddf.boot.common.websocket.model.MessageRequest;
import com.ddf.boot.common.websocket.model.MessageResponse;
import com.ddf.boot.common.websocket.service.WsMessageService;
import com.ddf.boot.quick.common.redis.RedisRequestDefinition;
import com.ddf.boot.quick.model.dto.PublishUniqueNameDTO;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快速开始控制器，用于演示某些功能的使用方式$
 *
 * @author dongfang.ding
 * @menu 项目快速开始演示类
 * @date 2020/8/15 0015 17:30
 */
@RestController
@RequestMapping("quick-start")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class QuickStartController {

    private final WsMessageService wsMessageService;

    private final RedisTemplateHelper redisTemplateHelper;

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisBloomFilter<String> uniqueNameBloomFilter;

    private final RedisTopic addUniqueTopic;

    public static Integer SHARD_INT = 0;

    @Autowired(required = false)
    @Qualifier(value = ZookeeperDistributedLock.BEAN_NAME)
    private DistributedLock zookeeperDistributedLock;
    @Autowired(required = false)
    @Qualifier(value = RedisDistributedLock.BEAN_NAME)
    private DistributedLock redisDistributedLock;


    /**
     * 快速测试，空接口，可以用这个接口和业务接口的压测结果对比
     *
     * @return
     */
    @GetMapping("quickTest")
    public Boolean quickTest() {
        return Boolean.TRUE;
    }


    /**
     * 异常演示
     *
     * @return
     */
    @GetMapping("exceptionDemo")
    public Boolean exception() {
        throw new BusinessException("异常演示");
    }

    /**
     * 基于zk的分布式锁的演示
     *
     * @return
     * @throws LockingReleaseException
     * @throws LockingAcquireException
     */
    @GetMapping("zkDistributedLock")
    public Boolean distributedLock() throws Exception {
        return zookeeperDistributedLock.lockWork("/zk_distributedLock_demo", 1, TimeUnit.SECONDS, () -> {
            try {
                log.info("我获取到了zk锁，下面开始执行任务。。。。。。。。。。。。。");
                SHARD_INT ++;
                // 通过获取锁之后的睡眠，然后将请求发给第二个实例，进行演示，看程序是否会阻塞
                Thread.sleep(10000);
                log.info("共享int变量的值 = {}", SHARD_INT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }, () -> {
            log.error("获取zk分布式锁失败");
            return Boolean.FALSE;
        });
    }


    /**
     * 基于redis的分布式锁的演示
     *
     * @return
     * @throws LockingReleaseException
     * @throws LockingAcquireException
     */
    @GetMapping("redisDistributedLock")
    public Boolean redisDistributedLock() throws Exception {
        return redisDistributedLock.lockWork("/redis_distributedLock_demo", () -> {
            try {
                log.info("我获取到了redis锁，下面开始执行任务。。。。。。。。。。。。。");
                SHARD_INT ++;
                // 通过获取锁之后的睡眠，然后将请求发给第二个实例，进行演示，看程序是否会阻塞
                Thread.sleep(35000);
                log.info("共享int变量的值 = {}", SHARD_INT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Boolean.TRUE;
        }, () -> {
            log.error("获取zk分布式锁失败");
            return Boolean.FALSE;
        });
    }


    /**
     * 给在线的客户端发送消息
     *
     * @param messageRequest
     * @return
     */
    @PostMapping("sendWebSocketMessage")
    public MessageResponse<?> sendWebSocketMessage(@RequestBody MessageRequest<?> messageRequest) {
        return wsMessageService.executeCmd(messageRequest);
    }


    // 两个可以一起压测，可以看到redis中的数据结构是如何处理这一块的

    /**
     * 测试基于redis的分布式限流
     *
     * @return
     */
    @PostMapping("testRedisRateLimitA")
    public Boolean testRedisRateLimitA() {
        PreconditionUtil.checkArgument(
                redisTemplateHelper.tokenBucketRateLimitAcquire(RedisRequestDefinition.testRateLimitA),
                GlobalCallbackCode.RATE_LIMIT
        );
        return Boolean.TRUE;
    }


    /**
     * 测试基于redis的分布式限流
     *
     * @return
     */
    @PostMapping("testRedisRateLimitB")
    public Boolean testRedisRateLimitB() {
        PreconditionUtil.checkArgument(
                redisTemplateHelper.tokenBucketRateLimitAcquire(RedisRequestDefinition.testRateLimitB),
                GlobalCallbackCode.RATE_LIMIT
        );
        return Boolean.TRUE;
    }


    /**
     * 测试redis_cluster
     *
     * @return
     */
    @PostMapping("testRedisCluster")
    public Boolean testRedisCluster() {
        stringRedisTemplate.opsForValue().set("string_testRedisCluster", System.currentTimeMillis() + "");
        stringRedisTemplate.opsForZSet().add("zset_testRedisCluster", "testRedisCluster", System.currentTimeMillis());
        stringRedisTemplate.opsForSet().add("set_testRedisCluster", "testRedisCluster");
        return Boolean.TRUE;
    }


    /**
     * 测试布隆过滤器
     *
     * @param uniqueName
     * @return
     */
    @PostMapping("addUniqueName")
    public Boolean addUniqueName(@RequestParam String uniqueName) {
        if (!uniqueNameBloomFilter.contains(uniqueName)) {
            final boolean add = uniqueNameBloomFilter.add(uniqueName);
            if (add) {
                addUniqueTopic.publish(PublishUniqueNameDTO.builder()
                        .uniqueName(uniqueName)
                        .bizTime(new Date())
                        .build());
            }
            return add;
        }
        return Boolean.FALSE;
    }
}
