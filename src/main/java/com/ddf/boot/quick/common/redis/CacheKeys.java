package com.ddf.boot.quick.common.redis;

import com.ddf.boot.common.redis.constant.ApplicationNamedKeyGenerator;

/**
 * 缓存key生成
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:25
 */
public interface CacheKeys {


    /**
     * 获取验证码缓存key
     *
     * @param tokenId 标识当前的一次form请求
     * @return
     */
    static String getCaptchaKey(String tokenId) {
        return ApplicationNamedKeyGenerator.genKey("captcha", tokenId);
    }
}
