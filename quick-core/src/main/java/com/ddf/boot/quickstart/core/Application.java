package com.ddf.boot.quickstart.core;

import com.ddf.boot.common.authentication.annotation.EnableAuthenticate;
import com.ddf.boot.common.core.logaccess.EnableLogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
@EnableLogAspect
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
