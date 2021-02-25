package com.ddf.boot.quick.controller.features;

import com.ddf.boot.common.ext.sms.domain.AliYunSmsRequest;
import com.ddf.boot.common.ext.sms.helper.AliYunSmsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>短信服务基本演示</p >
 *
 * @author Snowball
 * @version 1.0
 * @menu 阿里云短信平台相关
 * @date 2020/10/19 09:53
 */
@RestController
@RequestMapping("sms")
public class SmsController {


    @Autowired
    private AliYunSmsHelper aliYunSmsHelper;

    /**
     * 发送短信验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping("sendSms")
    public String sendSms(String mobile) {
        AliYunSmsRequest aliYunSmsRequest = new AliYunSmsRequest();
        aliYunSmsRequest.setPhoneNumbers(mobile);
        aliYunSmsRequest.setUseRandomCode(true);
        aliYunSmsHelper.sendSms(aliYunSmsRequest);
        return aliYunSmsRequest.getTemplateParam();
    }

}
