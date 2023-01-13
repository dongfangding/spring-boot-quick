package com.ddf.boot.quickstart.core.repository;

import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.api.util.JsonUtil;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.consts.RedisKeys;
import com.ddf.boot.quickstart.api.dto.EmailToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>通用的或者一些边缘业务的仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/28 15:20
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class CommonRepository {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 设置短信验证码
     *
     * @param mobile
     * @param uuid
     * @param randomCode
     * @return
     */
    public void setSmsCode(String mobile, String uuid, String randomCode) {
        stringRedisTemplate.opsForValue().set(
                RedisKeys.getSmsCodeKey(mobile, uuid), randomCode,
                RedisKeyEnum.SMS_CODE_KEY.getTimeout());
    }

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param uuid
     * @return
     */
    public String getSmsCode(String mobile, String uuid) {
        return stringRedisTemplate.opsForValue().get(RedisKeys.getSmsCodeKey(mobile, uuid));
    }

    /**
     * 设置邮箱激活token
     *
     * @param email
     * @param token
     * @return
     */
    public void setEmailActiveToken(String email, String token, Long userId) {
        final EmailToken emailToken = EmailToken.of(userId, email);
        stringRedisTemplate.opsForValue().set(RedisKeys.getEmailActiveTokenKey(token), JsonUtil.asString(emailToken), RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getTimeout());
    }

    /**
     * 根据邮箱激活token获取对应用户id和邮箱
     *
     * @param token
     * @return
     */
    public EmailToken getEmailActiveToken(String token) {
        final String value = stringRedisTemplate.opsForValue().get(RedisKeys.getEmailActiveTokenKey(token));
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JsonUtil.toBean(value, EmailToken.class);
    }
}
