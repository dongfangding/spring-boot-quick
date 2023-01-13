package com.ddf.boot.quickstart.core.application.impl;

import com.ddf.boot.common.core.encode.BCryptPasswordEncoder;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.request.auth.UserRegistryRequest;
import com.ddf.boot.quickstart.api.request.common.SmsCodeVerifyRequest;
import com.ddf.boot.quickstart.core.application.UserApplicationService;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.entity.UserProgress;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
import com.ddf.boot.quickstart.core.repository.PlayerProgressRepository;
import com.ddf.boot.quickstart.core.repository.UserInfoRepository;
import com.ddf.boot.quickstart.core.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>用户业务</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:52
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {
    private final UserInfoRepository userInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PlayerProgressRepository playerProgressRepository;
    private final CommonHelper commonHelper;
    private final UserInfoService userInfoService;
    private final ApplicationProperties applicationProperties;

    @Override
    public void registry(UserRegistryRequest request) {
        PreconditionUtil.checkArgument(!userInfoRepository.exitsByMobile(request.getMobile()),
                ApplicationExceptionCode.MOBILE_IS_USED);
        // 短信验证码校验
        final SmsCodeVerifyRequest verifyRequest = SmsCodeVerifyRequest.builder()
                .mobile(request.getMobile())
                .mobileCode(request.getMobileCode())
                .uuid(request.getUuid())
                .build();
        commonHelper.verifySmsCode(verifyRequest);
        UserInfo userInfo = userInfoService.registerUserInfo(request.getMobile(), bCryptPasswordEncoder.encode(request.getPassword()),
                applicationProperties.getDefaultAvatarUrl());

        // 增加用户进度条时间记录
        final UserProgress progress = new UserProgress();
        progress.setUserId(userInfo.getId());
        playerProgressRepository.insertPlayerProgress(progress);
    }
}
