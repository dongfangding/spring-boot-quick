package com.ddf.boot.quickstart.core.helper;

import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.common.api.model.captcha.request.CaptchaRequest;
import com.ddf.boot.common.api.model.captcha.response.ApplicationCaptchaResult;
import com.ddf.boot.common.authentication.config.AuthenticationProperties;
import com.ddf.boot.common.core.helper.EnvironmentHelper;
import com.ddf.boot.common.redis.helper.RedisTemplateHelper;
import com.ddf.boot.quickstart.core.repository.UserInfoRepository;
import com.ddf.common.captcha.helper.CaptchaHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>通用帮助类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 22:58
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CommonHelper {

    private final CaptchaHelper captchaHelper;

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    public ApplicationCaptchaResult generateCaptcha(CaptchaRequest request) {
        return ApplicationCaptchaResult.fromCaptchaResult(captchaHelper.generate(request));
    }

    /**
     * 验证码校验
     *
     * @param request
     */
    public void verifyCaptcha(CaptchaCheckRequest request) {
        captchaHelper.check(CaptchaCheckRequest
                .builder()
                .uuid(request.getUuid())
                .verification(request.isVerification())
                .captchaVerification(request.getCaptchaVerification())
                .captchaType(request.getCaptchaType())
                .verifyCode(request.getVerifyCode())
                .build());
    }
}
