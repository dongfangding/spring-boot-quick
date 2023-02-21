package com.ddf.boot.quickstart.core.controller;

import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.quickstart.api.request.auth.ModifyPasswordRequest;
import com.ddf.boot.quickstart.api.request.user.CompleteUserInfoRequest;
import com.ddf.boot.quickstart.api.request.user.EmailVerifyRequest;
import com.ddf.boot.quickstart.api.response.user.PersonalInfoResponse;
import com.ddf.boot.quickstart.core.application.UserApplicationService;
import com.ddf.boot.quickstart.core.client.MailClient;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
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
 * <p>用户控制器</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:56
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserInfoController {

    private final UserApplicationService userApplicationService;
    private final CommonHelper commonHelper;
    private final MailClient mailClient;
    private final ApplicationProperties applicationProperties;

    /**
     * 心跳
     *
     * @return
     */
    @PostMapping("heartbeat")
    public ResponseData<Void> heartbeat() {
        userApplicationService.heartBeat(UserContextUtil.getLongUserId());
        return ResponseData.empty();
    }

    /**
     * 完善用户信息
     *
     * @param request
     */
    @PostMapping("completeInfo")
    public ResponseData<PersonalInfoResponse> completeInfo(@RequestBody @Validated CompleteUserInfoRequest request) {
        return ResponseData.success(userApplicationService.completeInfo(request));
    }

    /**
     * 单独发送邮箱验证邮件
     *
     * @param request
     */
    @PostMapping("sendEmailVerify")
    public ResponseData<Void> sendEmailVerify(@RequestBody @Validated EmailVerifyRequest request) {
        mailClient.sendEmailActive(applicationProperties.getApplicationChineseName(), UserContextUtil.getLongUserId(), request.getEmail());
        return ResponseData.empty();
    }

    /**
     * 验证邮箱
     *
     * @param response
     * @param token
     */
    @GetMapping("verifyEmailActiveToken")
    public ResponseData<Void> verifyEmailActiveToken(HttpServletResponse response, @RequestParam String token) {
        commonHelper.verifyEmailActiveToken(response, token);
        return ResponseData.empty();
    }

    /**
     * 个人中心
     */
    @GetMapping("personalInfo")
    public ResponseData<PersonalInfoResponse> personalInfo() {
        return ResponseData.success(userApplicationService.personalInfo());
    }


    /**
     * 修改密码
     *
     * @param request
     */
    @PostMapping("modifyPassword")
    public ResponseData<Void> modifyPassword(@RequestBody @Validated ModifyPasswordRequest request) {
        userApplicationService.modifyPassword(request);
        return ResponseData.empty();
    }
}
