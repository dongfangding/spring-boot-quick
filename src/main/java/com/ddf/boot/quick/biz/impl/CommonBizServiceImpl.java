package com.ddf.boot.quick.biz.impl;

import com.ddf.boot.common.core.util.IdsUtil;
import com.ddf.boot.quick.biz.ICommonBizService;
import com.ddf.boot.quick.common.redis.CacheKeys;
import com.ddf.boot.quick.model.request.CaptchaRequest;
import com.ddf.boot.quick.model.response.CaptchaResponse;
import com.ddf.common.captcha.helper.CaptchaHelper;
import com.ddf.common.captcha.model.CaptchaResult;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class CommonBizServiceImpl implements ICommonBizService {

    private final StringRedisTemplate stringRedisTemplate;

    private final CaptchaHelper captchaHelper;

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @Override
    public CaptchaResponse generateCaptcha(CaptchaRequest request) {
        // data:image/jpeg;base64,
        final CaptchaResult result = captchaHelper.generateMath();
        final String tokenId = IdsUtil.getNextStrId();
        // 设置有效期
        stringRedisTemplate.opsForValue()
                .set(CacheKeys.getCaptchaKey(tokenId), result.getVerifyCode(), Duration.ofMinutes(1));
        return new CaptchaResponse().setWidth(result.getWidth())
                .setHeight(result.getHeight())
                .setBase64(result.getImageBase64())
                .setTokenId(tokenId);
    }
}
