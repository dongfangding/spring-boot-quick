package com.ddf.boot.quick.controller;

import com.ddf.boot.common.core.exception200.BusinessException;
import com.ddf.boot.common.core.exception200.GlobalCallbackCode;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import com.ddf.boot.common.lock.DistributedLock;
import com.ddf.boot.common.lock.exception.LockingAcquireException;
import com.ddf.boot.common.lock.exception.LockingReleaseException;
import com.ddf.boot.common.redis.helper.RedisTemplateHelper;
import com.ddf.boot.common.websocket.model.MessageRequest;
import com.ddf.boot.common.websocket.model.MessageResponse;
import com.ddf.boot.common.websocket.service.WsMessageService;
import com.ddf.boot.quick.common.redis.RedisRequestDefinition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 快速开始控制器，用于演示某些功能的使用方式$
 *
 * @menu 项目快速开始演示类
 * @author dongfang.ding
 * @date 2020/8/15 0015 17:30
 */
@RestController
@RequestMapping("quick-start")
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class QuickStartController {

    private final SnowflakeServiceHelper snowflakeServiceHelper;

    private final WsMessageService wsMessageService;

    private final RedisTemplateHelper redisTemplateHelper;

    private final StringRedisTemplate stringRedisTemplate;

    @Resource(name = "zookeeperDistributedLock")
    private DistributedLock distributedLock;


    /**
     * 异常演示
     * @return
     */
    @GetMapping("exceptionDemo")
    public Boolean exception() {
        throw new BusinessException("异常演示");
    }


    /**
     * 基于zk的雪花id的使用方式
     * @return
     */
    @GetMapping("getSnowflakeId")
    public Long getSnowflakeId() {
        return snowflakeServiceHelper.getLongId();
    }


    /**
     * 基于zk的分布式锁的演示
     * @return
     * @throws LockingReleaseException
     * @throws LockingAcquireException
     */
    @GetMapping("distributedLock")
    public Boolean distributedLock() throws LockingReleaseException, LockingAcquireException {
        distributedLock.lockWork("/distributedLock_demo", 10, TimeUnit.SECONDS, () -> {
            try {
                log.info("我获取到了锁，下面开始执行任务。。。。。。。。。。。。。");
                // 通过获取锁之后的睡眠，然后将请求发给第二个实例，进行演示，看程序是否会阻塞
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return Boolean.TRUE;
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
     * @return
     */
    @PostMapping("testRedisRateLimitA")
    public Boolean testRedisRateLimitA() {
        PreconditionUtil.checkArgument(redisTemplateHelper.rateLimitAcquire(RedisRequestDefinition.testRateLimitA), GlobalCallbackCode.RATE_LIMIT);
        return Boolean.TRUE;
    }


    /**
     * 测试基于redis的分布式限流
     * @return
     */
    @PostMapping("testRedisRateLimitB")
    public Boolean testRedisRateLimitB() {
        PreconditionUtil.checkArgument(redisTemplateHelper.rateLimitAcquire(RedisRequestDefinition.testRateLimitB), GlobalCallbackCode.RATE_LIMIT);
        return Boolean.TRUE;
    }


    /**
     * 测试redis_cluster
     * @return
     */
    @PostMapping("testRedisCluster")
    public Boolean testRedisCluster() {
        stringRedisTemplate.opsForValue().set("string_testRedisCluster", System.currentTimeMillis() + "");
        stringRedisTemplate.opsForZSet().add("zset_testRedisCluster", "testRedisCluster", System.currentTimeMillis());
        stringRedisTemplate.opsForSet().add("set_testRedisCluster", "testRedisCluster");
        return Boolean.TRUE;
    }
}
