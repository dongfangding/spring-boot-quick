package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.common.api.model.captcha.request.CaptchaRequest;
import com.ddf.boot.common.api.model.captcha.response.ApplicationCaptchaResult;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.core.resolver.MultiArgumentResolver;
import com.ddf.boot.common.core.util.BeanCopierUtils;
import com.ddf.boot.quickstart.api.request.common.SendSmsCodeRequest;
import com.ddf.boot.quickstart.api.request.user.EmailVerifyRequest;
import com.ddf.boot.quickstart.api.response.common.ApplicationSmsSendResponse;
import com.ddf.boot.quickstart.api.response.sys.SysDictResponse;
import com.ddf.boot.quickstart.core.client.MailClient;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
import com.ddf.boot.quickstart.core.repository.SysDictRepository;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>通用工具类</p >
 *
 * @menu 通用工具类
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 23:02
 */
@RestController
@RequestMapping("/common")
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class CommonController {

    private final CommonHelper commonHelper;
    private final SysDictRepository sysDictRepository;
    private final MailClient mailClient;
    private final ApplicationProperties applicationProperties;

    @GetMapping("listDict")
    public List<SysDictResponse> listDict(@RequestParam String dictType) {
        return BeanCopierUtils.copy(sysDictRepository.listDictByCodeFromCache(dictType), SysDictResponse.class);
    }

    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @PostMapping("generateCaptcha")
    public ApplicationCaptchaResult generateCaptcha(@MultiArgumentResolver @Validated CaptchaRequest request) {
        return commonHelper.generateCaptcha(request);
    }

    /**
     * 验证码校验
     *
     * @param request
     */
    @PostMapping("checkCaptcha")
    public void checkCaptcha(@RequestBody @Validated CaptchaCheckRequest request) {
        commonHelper.verifyCaptcha(request);
    }

    /**
     * 发送短信验证码
     *
     * @param sendSmsCodeRequest
     * @return
     */
    @PostMapping("/sendSmsCode")
    public ApplicationSmsSendResponse sendSmsCode(@RequestBody @Validated SendSmsCodeRequest sendSmsCodeRequest) {
        return commonHelper.sendAndLoadSmsCodeWithLimit(sendSmsCodeRequest);
    }

    /**
     * 单独发送邮箱验证邮件
     *
     * @param request
     */
    @PostMapping("sendEmailVerify")
    public void sendEmailVerify(@RequestBody @Validated EmailVerifyRequest request) {
        mailClient.sendEmailActive(applicationProperties.getApplicationChineseName(), UserContextUtil.getUserId(), request.getEmail());
    }

    /**
     * 验证邮箱
     *
     * @param response
     * @param token
     */
    @GetMapping("verifyEmailActiveToken")
    public void verifyEmailActiveToken(HttpServletResponse response, @RequestParam String token) {
        commonHelper.verifyEmailActiveToken(response, token);
    }

}
