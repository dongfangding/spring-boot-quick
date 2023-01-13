package com.ddf.boot.quickstart.core.client;

import cn.hutool.core.util.RandomUtil;
import com.ddf.boot.common.ext.sms.SmsApi;
import com.ddf.boot.common.ext.sms.model.SmsSendRequest;
import com.ddf.boot.common.ext.sms.model.SmsSendResponse;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>短信客户端</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/11/25 15:58
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SmsClient {

    private final SmsApi aliYunSmsApiImpl;
    private final ApplicationProperties applicationProperties;

    /**
     * 发送短信内容
     *
     * @param request
     * @return
     */
    public SmsSendResponse send(SmsSendRequest request) {
        if (applicationProperties.isSmsCodeMockEnabled()) {
            final String code = RandomUtil.randomInt(100000, 999999) + "";
            log.info("发送短信验证码，mobile = {}, code = {}", request.getPhoneNumbers(), code);
            final SmsSendResponse response = new SmsSendResponse();
            response.setTemplateParam("");
            response.setRandomCode(code);
            return response;
        }
        return aliYunSmsApiImpl.send(request);
    }
}
