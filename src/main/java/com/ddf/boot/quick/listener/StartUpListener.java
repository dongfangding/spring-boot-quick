package com.ddf.boot.quick.listener;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/08/12 13:42
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ThreadPoolTaskExecutor heartBeatExecutor;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>application refresh>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("开始分配心跳任务>>>>>>>>>>>>>>>>>>>>>>>>");
        final AtomicInteger integer = new AtomicInteger();
        for (int i = 0; i < 20000; i++) {
            heartBeatExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("发送心跳, 当前值={}, >>>>>>>>>>>>>>>>>>>>>.", integer.addAndGet(1));
            });
        }
        log.info("心跳任务分配完成>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
