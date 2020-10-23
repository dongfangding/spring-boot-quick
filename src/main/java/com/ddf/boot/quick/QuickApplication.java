package com.ddf.boot.quick;

import com.ddf.boot.common.core.logaccess.EnableLogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @MapperScan 要保证只扫描继承到BaseMapper的类，否则正常的接口会抛出异常Invalid bound statement (not found)
 *
 * @author dongfang.ding
 * @date 2019/12/7 0007 23:28
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.ddf.boot.quick.mapper"})
//@EnableJwt
@EnableLogAspect(slowTime = 3000)
@EnableMongoRepositories("com.ddf.boot.quick.mongo.repository")
public class QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickApplication.class, args);
    }
}
