package com.ddf.boot.quick.controller;

import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import com.ddf.boot.common.lock.DistributedLock;
import com.ddf.boot.common.lock.exception.LockingAcquireException;
import com.ddf.boot.common.lock.exception.LockingReleaseException;
import com.ddf.boot.quick.websocket.model.MessageRequest;
import com.ddf.boot.quick.websocket.model.MessageResponse;
import com.ddf.boot.quick.websocket.service.WsMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 快速开始控制器，用于演示某些功能的使用方式$
 *
 * @author dongfang.ding
 * @date 2020/8/15 0015 17:30
 */
@RestController
@RequestMapping("quick-start")
@Slf4j
public class QuickStartController {

    @Autowired
    private SnowflakeServiceHelper snowflakeServiceHelper;

    @Autowired
    private WsMessageService wsMessageService;

    @Resource(name = "zookeeperDistributedLock")
    private DistributedLock distributedLock;


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
}
