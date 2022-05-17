package com.ddf.boot.quick.helper;

import com.aliyuncs.CommonResponse;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.ext.sms.domain.AliYunSmsRequest;
import com.ddf.boot.common.ext.sms.helper.AliYunSmsHelper;
import com.ddf.boot.quick.model.request.SendSmsCodeRequest;
import com.ddf.common.captcha.helper.CaptchaHelper;
import com.ddf.common.captcha.model.CaptchaRequest;
import com.ddf.common.captcha.model.CaptchaResult;
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

    private final AliYunSmsHelper aliYunSmsHelper;
    private final CaptchaHelper captchaHelper;

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    public CaptchaResult generateCaptcha(CaptchaRequest request) {
        return captchaHelper.generate(request);
    }

    /**
     * 发送短信验证码
     *
     * @param sendSmsCodeRequest
     */
    public void sendSmsCode(SendSmsCodeRequest sendSmsCodeRequest) {
        PreconditionUtil.requiredParamCheck(sendSmsCodeRequest);
        final AliYunSmsRequest request = new AliYunSmsRequest();
        request.setPhoneNumbers(sendSmsCodeRequest.getMobile());
        request.setUseRandomCode(true);
        final CommonResponse response = aliYunSmsHelper.sendSms(request);
        System.out.println("response = " + response);
    }

}
