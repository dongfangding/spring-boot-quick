package com.ddf.boot.quickstart.api.consts;

import com.ddf.boot.common.api.constraint.redis.NumberShardingRule;
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

    /**
     * 用户心跳详情信息, hash结构
     * hash key 为第0个参数，使用这个参数来路由分片key
     */
    USER_HEART_BEAT_DETAIL("user:user_heart_beat_detail", RedisKeyTypeEnum.HASH, NumberShardingRule.of(0, 10)),

    /**
     * 每日心跳在线时长，score为用户当天累计在线时间，隔天的话会累计到第二天里
     * %s 当前日期天，格式为yyyyMMdd
     */
    DAILY_HEART_BEAT("user:daily_heart_beat:%s", Duration.ofDays(30), RedisKeyTypeEnum.ZSET),

    /**
     * 连续心跳记录，用户连续在线时间，只要一直心跳，不会因为隔天而清零，心跳时间如果在间隔范围内会一直累加，如果超过间隔时间，则重置；
     * 使用时，还需要自行判定当前时间有没有超过最后一次心跳的间隔时间，因为如果下线后不再上线，这个时间如果没有定时的话，时间是不准的
     */
    CONTINUE_HEART_BEAT("user:continue_heart_beat", RedisKeyTypeEnum.ZSET),

    /**
     * 在线用户map维护
     */
    ONLINE_USER_MAP("user:online_user_map", RedisKeyTypeEnum.SET),

    /**
     * 用户信息
     * %s 用户id
     */
    USER_INFO("user:user_info:%s", Duration.ofDays(1), RedisKeyTypeEnum.STRING),

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
