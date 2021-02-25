package com.ddf.boot.quick.common.properties;

import com.ddf.boot.common.limit.ratelimit.keygenerator.GlobalRateLimitKeyGenerator;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>限流全局参数</p >
 *
 * 如果是cloud环境，可以加上注解@RefreshScope
 *
 * @author dongfang.ding
 * @version 1.0
 * @date 2021/02/24 13:42
 */
@Data
@Component
@ConfigurationProperties(prefix = "customs.limit.rate-limit")
public class RateLimitExtraProperties {

    public static final String BEAN_NAME = "rateLimitProperties";

    /**
     * 限流key生成规则bean_name
     */
    private String keyGenerators = GlobalRateLimitKeyGenerator.BEAN_NAME;

    /**
     * 令牌桶最大数量
     */
    private Integer max = 1;

    /**
     * 令牌恢复速率，单位秒
     */
    private Integer rate = 1;
}
