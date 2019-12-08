package com.ddf.boot.quick;

import com.ddf.common.jwt.config.EnableJwt;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author dongfang.ding
 * @date 2019/12/7 0007 23:28
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ddf")
@MapperScan(basePackages = {"com.ddf.boot.quick.mapper", "com.ddf.common.security.mapper"})
@EnableJwt
public class QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickApplication.class, args);
    }
}
