package com.ddf.boot.quick;

import com.ddf.boot.common.core.logaccess.EnableLogAspect;
import com.ddf.boot.common.jwt.config.EnableJwt;
import com.ddf.boot.common.limit.ratelimit.annotation.EnableRateLimit;
import com.ddf.boot.common.limit.repeatable.annotation.EnableRepeatable;
import com.ddf.boot.common.limit.repeatable.validator.RedisRepeatableValidator;
import org.apache.shardingsphere.elasticjob.lite.spring.boot.job.ElasticJobLiteAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author dongfang.ding
 * @MapperScan 要保证只扫描继承到BaseMapper的类，否则正常的接口会抛出异常Invalid bound statement (not found)
 * @date 2019/12/7 0007 23:28
 */
@SpringBootApplication(exclude = {ElasticJobLiteAutoConfiguration.class})
@ComponentScan(value = "com.ddf.boot")
@MapperScan(basePackages = {"com.ddf.boot.quick.mapper"})
@EnableJwt
@EnableLogAspect(slowTime = 3000)
@EnableMongoRepositories("com.ddf.boot.quick.features.mongo.repository")
@EnableRepeatable(globalValidator = RedisRepeatableValidator.BEAN_NAME)
@EnableRateLimit(cloudRefresh = false, max = 10, rate = 5)
public class QuickApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickApplication.class, args);
    }
}

/**
 *
 * TODO
 * 1. 编辑时自动填充的modifyBy和modifyTime的值是空的
 * 2. 自定义的mapper方法，插入或更新时无法填充通用属性
 *
 *
 *
 *
 *
 *
 *
 *
 */
