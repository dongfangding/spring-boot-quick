package com.ddf.boot.quickstart.core;

import com.alibaba.druid.spring.boot3.autoconfigure.DruidDataSourceAutoConfigure;
import com.ddf.boot.common.mvc.logaccess.EnableLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
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
@MapperScan("com.ddf.boot.quickstart.core.infra.mapper")
@EnableAsync
@EnableScheduling
@EnableLogAspect(slowTime = 3000)
@Slf4j
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(() -> {
                    log.info("shutdown hook executing..............");
                }));
    }
}
