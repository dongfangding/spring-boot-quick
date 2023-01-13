package com.ddf.boot.quickstart.api.consts;

/**
 * <p>redis key 定义</p >
 *
 * 这个类的目的是还是想通过方法来生成key， 这样统一用到key的时候通过这个方法来收口，万一key规则变动，比较容易识别。
 * 这样方便辨识key规则中的变量到底存的是什么
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 20:15
 */
public interface RedisKeys {

    /**
     * 短信验证码key
     * %s mobile
     * %s 随机字符串
     */
    String SMS_CODE_KEY = "common:sms_code:%s:%s";

    /**
     * 短信验证码限流key
     * %s 限流的目标关键字， 如用户uid, 或者ip，大部分为uid
     */
    String SMS_RATE_LIMIT_KEY = "common:sms_code:rate_limit:%s";

    /**
     * 邮箱激活token key, value是邮箱，方便通过token找回邮箱
     * %s token
     */
    String EMAIL_ACTIVE_TOKEN_KEY = "common:email_active_token:%s";

    /**
     * 团购统计相关key
     * %s 团购id
     */
    String GROUP_STATISTICS_KEY = "group:statistics:hash:%s";


    /**
     * 获取短信验证码key
     *
     * @param mobile
     * @param random
     * @return
     */
    static String getSmsCodeKey(String mobile, String random) {
        return String.format(SMS_CODE_KEY, mobile, random);
    }

    /**
     * 短信验证码限流key
     *
     * @param uid
     * @return
     */
    static String getSmsRateLimitKey(String uid) {
        return String.format(SMS_RATE_LIMIT_KEY, uid);
    }

    /**
     * 获取邮箱激活token key
     *
     * @param token
     * @return
     */
    static String getEmailActiveTokenKey(String token) {
        return String.format(EMAIL_ACTIVE_TOKEN_KEY, token);
    }

    /**
     * 获取团购统计相关key
     *
     * @param groupId
     * @return
     */
    static String getGroupStatisticsKey(Long groupId) {
        return String.format(GROUP_STATISTICS_KEY, groupId);
    }

}
