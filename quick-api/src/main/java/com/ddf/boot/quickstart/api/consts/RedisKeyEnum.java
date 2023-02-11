package com.ddf.boot.quickstart.api.consts;

import com.ddf.boot.common.api.constraint.redis.RedisKeyConstraint;
import com.ddf.boot.common.api.constraint.redis.RedisShardingRule;
import com.ddf.boot.common.api.enums.RedisKeyTypeEnum;
import java.time.Duration;

/**
 * <p>redis key 定义枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 20:36
 */
public enum RedisKeyEnum implements RedisKeyConstraint {

    /**
     * 短信验证码key
     * %s mobile
     * %s 随机字符串
     */
    SMS_CODE_KEY("common:sms_code:%s:%s", Duration.ofSeconds(300), RedisKeyTypeEnum.STRING),

    /**
     * 短信验证码限流key
     * %s 限流的目标关键字， 如用户uid, 或者ip，大部分为uid
     */
    SMS_RATE_LIMIT_KEY("common:sms_code:rate_limit:%s", RedisKeyTypeEnum.STRING),

    /**
     * 邮箱验证码key
     * %s token
     */
    EMAIL_ACTIVE_TOKEN_KEY("common:email_active_token:%s", Duration.ofSeconds(300), RedisKeyTypeEnum.STRING),
    ;

    /**
     * key模板，变量使用%s代替
     * 如sms_code:%s:%s
     */
    private final String template;

    /**
     * 过期秒数,这里不会根据这个做什么事情，自己定义自己使用就行，这里主要是一些固定业务使用的key过期时间是固定的，就在这里当常量定义了
     * 如短信验证码，需要的是一个常量的过期时间，那就在这里定义，用的时候引用这里就行，其它情况下意义不大
     */
    private Duration ttl;

    private final RedisKeyTypeEnum keyType;

    /**
     * key的分片规则
     */
    private RedisShardingRule redisShardingRule;

    RedisKeyEnum(String template, RedisKeyTypeEnum keyType) {
        this.template = template;
        this.ttl = Duration.ofSeconds(-1);
        this.keyType = keyType;
    }

    RedisKeyEnum(String template, Duration ttl, RedisKeyTypeEnum keyType) {
        this.template = template;
        this.ttl = ttl;
        this.keyType = keyType;
    }

    RedisKeyEnum(String template, RedisKeyTypeEnum keyType, RedisShardingRule redisShardingRule) {
        this.template = template;
        this.keyType = keyType;
        this.redisShardingRule = redisShardingRule;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public Duration getTtl() {
        return ttl;
    }

    @Override
    public RedisKeyTypeEnum getRedisKeyType() {
        return keyType;
    }

    @Override
    public <S, M> RedisShardingRule<S, M> getRedisShardingRule() {
        return redisShardingRule;
    }
}
