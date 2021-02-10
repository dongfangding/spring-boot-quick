package com.ddf.boot.quick.biz.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.ddf.boot.quick.biz.CommonBizService;
import com.ddf.boot.quick.common.redis.CacheKeys;
import com.ddf.boot.quick.model.bo.CaptchaRequest;
import com.ddf.boot.quick.model.vo.CaptchaResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 通用业务服务类实现
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 12:11
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class CommonBizServiceImpl implements CommonBizService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @Override
    public CaptchaResponse generateCaptcha(CaptchaRequest request) {
        // data:image/jpeg;base64,
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 80, 4, 200);
        // 设置有效期
        stringRedisTemplate.opsForValue().set(CacheKeys.getCaptchaKey(request.getFormId()), captcha.getCode(),
                Duration.ofMinutes(2));
        return new CaptchaResponse().setWidth(200).setHeight(80).setBase64(captcha.getImageBase64());
    }
}