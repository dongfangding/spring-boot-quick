package com.ddf.boot.quickstart.core.config;

import com.ddf.boot.common.core.helper.ThreadBuilderHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池配置类$
 *
 * @author dongfang.ding
 * @date 2019/12/10 0010 20:11
 */
@Configuration
public class QuickThreadConfig {

    /**
     * 用户登录记录线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor userLoginLogExecutor() {
        return ThreadBuilderHelper.buildThreadExecutor("user-login-executor-", 30, 1000);
    }

    /**
     * 邮件发送
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor mailSendExecutor() {
        return ThreadBuilderHelper.buildThreadExecutor("mail-send-executor-", 30, 100);
    }

    /**
     * 心跳检测类，目前是在启动时执行一段时间的任务，然后再未完成任务时对任务进行停机，演示是否可以优雅停机
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor heartBeatExecutor() {
        return ThreadBuilderHelper.buildThreadExecutor("heart-bet-executor-", 60, 1000,1, 1, true);
    }
}
