package com.ddf.boot.quickstart.core;

import com.ddf.boot.common.authentication.annotation.EnableAuthenticate;
import com.ddf.boot.common.core.logaccess.EnableLogAspect;
import com.ddf.boot.common.limit.ratelimit.annotation.EnableRateLimit;
import com.ddf.boot.common.limit.repeatable.annotation.EnableRepeatable;
import com.ddf.boot.common.limit.repeatable.validator.RedisRepeatableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 11:49
 */
@SpringBootApplication
@MapperScan("com.ddf.boot.quickstart.core.mapper")
@EnableAuthenticate
@EnableAsync
@EnableScheduling
@EnableLogAspect(slowTime = 3000)
@EnableRateLimit()
@EnableRepeatable(globalValidator = RedisRepeatableValidator.BEAN_NAME)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
