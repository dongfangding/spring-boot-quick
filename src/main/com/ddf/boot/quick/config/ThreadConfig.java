package com.ddf.boot.quick.config;

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
public class ThreadConfig {


    /**
     * 用户登录记录线程池
     *
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor userLoginLogExecutor() {
        return buildThreadExecutor("user-login-executor-", 30, 1000);
    }

    /**
     * 构建线程参数
     * @param prefix
     * @param keepAliveSeconds
     * @param queueCapacity
     * @return
     */
    private ThreadPoolTaskExecutor buildThreadExecutor(String prefix, int keepAliveSeconds, int queueCapacity) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setThreadNamePrefix(prefix);
        threadPoolTaskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2 + 1);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        return threadPoolTaskExecutor;
    }

}
