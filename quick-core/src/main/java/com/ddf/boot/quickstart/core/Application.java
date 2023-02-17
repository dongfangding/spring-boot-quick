package com.ddf.boot.quickstart.core;

import com.ddf.boot.common.authentication.annotation.EnableAuthenticate;
import com.ddf.boot.common.core.logaccess.EnableLogAspect;
import com.ddf.boot.common.limit.ratelimit.annotation.EnableRateLimit;
import com.ddf.boot.common.limit.repeatable.annotation.EnableRepeatable;
import com.ddf.boot.common.limit.repeatable.validator.RedisRepeatableValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.lite.spring.boot.job.ElasticJobLiteAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 11:49
 */
@SpringBootApplication(exclude = {ElasticJobLiteAutoConfiguration.class})
@MapperScan("com.ddf.boot.quickstart.core.mapper")
@EnableAuthenticate
@EnableAsync
@EnableScheduling
@EnableLogAspect(slowTime = 3000)
@EnableRepeatable(globalValidator = RedisRepeatableValidator.BEAN_NAME)
@EnableRateLimit(max = 1000, rate = 500)
@EnableElasticsearchRepositories(basePackages = {"com.ddf.boot.quickstart.core.features.es.repository"})
@Slf4j
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("shutdown hook executing..............");
        }));
    }

    /**
     * TODO
     * 考虑由服务端下发客户端唯一标识号， 然后全局管理，如果不存在， 则不处理请求
     * mock用户登录功能
     */
}
