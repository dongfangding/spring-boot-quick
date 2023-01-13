package com.ddf.boot.quickstart.api.consts;

import java.time.Duration;
import lombok.Getter;

/**
 * <p>redis key 定义枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 20:36
 */
public enum RedisKeyEnum {

    /**
     * 短信验证码key
     * %s mobile
     * %s 随机字符串
     */
    SMS_CODE_KEY(RedisKeys.SMS_CODE_KEY, Duration.ofSeconds(300)),

    /**
     * 邮箱验证码key
     */
    EMAIL_ACTIVE_TOKEN_KEY(RedisKeys.EMAIL_ACTIVE_TOKEN_KEY, Duration.ofSeconds(300)),

    ;

    /**
     * key模板，变量使用%s代替
     * 如sms_code:%s:%s
     */
    @Getter
    private final String template;

    /**
     * 过期秒数,这里不会根据这个做什么事情，自己定义自己使用就行，这里主要是一些固定业务使用的key过期时间是固定的，就在这里当常量定义了
     * 如短信验证码，需要的是一个常量的过期时间，那就在这里定义，用的时候引用这里就行，其它情况下意义不大
     */
    @Getter
    private final Duration timeout;

    RedisKeyEnum(String template, Duration timeout) {
        this.template = template;
        this.timeout = timeout;
    }

    RedisKeyEnum(String template) {
        this.template = template;
        this.timeout = Duration.ofSeconds(-1);
    }

    /**
     * 总觉得这种方式，会把生成key的规则放入到代码中，如果变动的话，会是灾难， 所以还是用其他散落key的方式，提供getKey的方法反而还更好维护
     *
     * @param args
     * @return
     */
    public String formatKey(String... args) {
        return String.format(template, args);
    }
}
