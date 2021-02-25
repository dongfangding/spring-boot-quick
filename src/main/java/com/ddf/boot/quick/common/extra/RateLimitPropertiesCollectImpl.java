package com.ddf.boot.quick.common.extra;

import com.ddf.boot.common.limit.ratelimit.extra.RateLimitPropertiesCollect;
import com.ddf.boot.common.limit.ratelimit.keygenerator.RateLimitKeyGenerator;
import com.ddf.boot.quick.common.properties.RateLimitExtraProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>用来外部注入填充限流组件参数的扩展接口实现类</p >
 *
 * @author dongfang.ding
 * @version 1.0
 * @date 2021/02/25 11:06
 */
@Component
public class RateLimitPropertiesCollectImpl implements RateLimitPropertiesCollect {

    @Autowired
    private RateLimitExtraProperties rateLimitExtraProperties;

    /**
     * 全局key生成规则,使用实现类bean的名称
     *
     * @return
     * @see RateLimitKeyGenerator
     */
    @Override
    public String getKeyGenerators() {
        return rateLimitExtraProperties.getKeyGenerators();
    }

    /**
     * 令牌桶最大数量
     *
     * @return
     */
    @Override
    public Integer getMax() {
        return rateLimitExtraProperties.getMax();
    }

    /**
     * 令牌恢复速率，单位秒
     *
     * @return
     */
    @Override
    public Integer getRate() {
        return rateLimitExtraProperties.getRate();
    }
}
