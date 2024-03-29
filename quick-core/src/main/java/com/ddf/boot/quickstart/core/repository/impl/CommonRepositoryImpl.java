package com.ddf.boot.quickstart.core.repository.impl;

import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.api.util.JsonUtil;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.dto.EmailToken;
import com.ddf.boot.quickstart.core.repository.CommonRepository;
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
public class CommonRepositoryImpl implements CommonRepository {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 设置短信验证码
     *
     * @param mobile
     * @param uuid
     * @param randomCode
     * @return
     */
    @Override
    public void setSmsCode(String mobile, String uuid, String randomCode) {
        stringRedisTemplate.opsForValue().set(
                RedisKeyEnum.SMS_CODE_KEY.getKey(mobile, uuid), randomCode,
                RedisKeyEnum.SMS_CODE_KEY.getTtl());
    }

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param uuid
     * @return
     */
    @Override
    public String getSmsCode(String mobile, String uuid) {
        return stringRedisTemplate.opsForValue().get(RedisKeyEnum.SMS_CODE_KEY.getKey(mobile, uuid));
    }

    /**
     * 设置邮箱激活token
     *
     * @param email
     * @param token
     * @return
     */
    @Override
    public void setEmailActiveToken(String email, String token, Long userId) {
        final EmailToken emailToken = EmailToken.of(userId, email);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getKey((token)), JsonUtil.asString(emailToken), RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getTtl());
    }

    /**
     * 根据邮箱激活token获取对应用户id和邮箱
     *
     * @param token
     * @return
     */
    @Override
    public EmailToken getEmailActiveToken(String token) {
        final String value = stringRedisTemplate.opsForValue().get(RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getKey(token));
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JsonUtil.toBean(value, EmailToken.class);
    }
}
