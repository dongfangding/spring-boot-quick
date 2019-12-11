package com.ddf.boot.quick.config;

import com.ddf.boot.common.helper.ThreadBuilderHelper;
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
}
