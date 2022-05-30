package com.ddf.boot.quick.helper;

import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.ext.sms.SmsApi;
import com.ddf.boot.common.ext.sms.model.SmsSendRequest;
import com.ddf.boot.common.ext.sms.model.SmsSendResponse;
import com.ddf.boot.quick.model.request.SendSmsCodeRequest;
import com.ddf.common.captcha.helper.CaptchaHelper;
import com.ddf.common.captcha.model.request.CaptchaRequest;
import com.ddf.common.captcha.model.response.ApplicationCaptchaResult;
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
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class CommonHelper {

    private final SmsApi aliYunSmsApiImpl;
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
     * 发送短信验证码
     *
     * @param sendSmsCodeRequest
     */
    public SmsSendResponse sendSmsCode(SendSmsCodeRequest sendSmsCodeRequest) {
        PreconditionUtil.requiredParamCheck(sendSmsCodeRequest);
        final SmsSendRequest request = new SmsSendRequest();
        request.setPhoneNumbers(sendSmsCodeRequest.getMobile());
        return aliYunSmsApiImpl.send(request);
    }

}
