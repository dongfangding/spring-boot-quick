package com.ddf.boot.quickstart.core.config;

import com.ddf.boot.common.core.helper.ThreadBuilderHelper;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <p>线程配置</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/01/02 13:15
 */
@Configurable
public class ThreadConfig {

    /**
     * 邮件发送
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor mailThreadPool() {
        return ThreadBuilderHelper.buildThreadExecutor("mail-send-executor-", 30, 500);
    }

    /**
     * 登录事件线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor loginPoolTaskExecutor() {
        return ThreadBuilderHelper.buildThreadExecutor("login-executor-", 300, 1000);
    }

    /**
     * 用户重新load缓存线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor userReloadCacheExecutor() {
        return ThreadBuilderHelper.buildThreadExecutor("user-reload-executor-", 300, 1000);
    }
}
