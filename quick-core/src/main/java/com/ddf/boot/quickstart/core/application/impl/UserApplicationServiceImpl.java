package com.ddf.boot.quickstart.core.application.impl;


import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.core.encode.BCryptPasswordEncoder;
import com.ddf.boot.common.core.helper.EnvironmentHelper;
import com.ddf.boot.common.core.util.BeanCopierUtils;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.request.sys.ModifyPasswordRequest;
import com.ddf.boot.quickstart.api.request.user.CompleteUserInfoRequest;
import com.ddf.boot.quickstart.api.response.user.PersonalInfoResponse;
import com.ddf.boot.quickstart.core.application.UserApplicationService;
import com.ddf.boot.quickstart.core.client.MailClient;
import com.ddf.boot.quickstart.core.client.UserClient;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.helper.CommonHelper;
import com.ddf.boot.quickstart.core.mapper.UserInfoMapper;
import com.ddf.boot.quickstart.core.model.cqrs.user.CompleteUserInfoCommand;
import com.ddf.boot.quickstart.core.repository.OnlineUserRepository;
import com.ddf.boot.quickstart.core.repository.UserInfoRepository;
import com.ddf.boot.quickstart.core.service.UserInfoService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final CommonHelper commonHelper;
    private final UserInfoService userInfoService;
    private final ApplicationProperties applicationProperties;
    private final UserClient userClient;
    private final MailClient mailClient;
    private final EnvironmentHelper environmentHelper;
    private final UserInfoMapper userInfoMapper;
    private final OnlineUserRepository onlineUserRepository;

    @Override
    public void heartBeat(Long userId) {
        // 处理心跳信息
        userInfoRepository.setHeartBeat(userId);
        // 将用户放入在线用户列表
        onlineUserRepository.putOnlineUser(userId);
    }

    @Override
    public PersonalInfoResponse completeInfo(CompleteUserInfoRequest request) {
        if (StrUtil.isAllEmpty(request.getEmail(), request.getNickname(), request.getAvatarUrl(), request.getAvatarThumbUrl())) {
            return personalInfo();
        }
        final UserInfo userInfo = userClient.currentUserInfo();
        final CompleteUserInfoCommand command = CompleteUserInfoCommand.builder()
                .id(userInfo.getId())
                .nickname(request.getNickname())
                .avatarUrl(request.getAvatarUrl())
                .avatarThumbUrl(request.getAvatarThumbUrl())
                .build();
        // 邮件未认证或者更改邮件重新发送激活邮件
        if (StringUtils.isNotBlank(request.getEmail()) &&
                (StringUtils.isBlank(userInfo.getEmail()) || !Objects.equals(request.getEmail(), userInfo.getEmail()))) {
            PreconditionUtil.checkArgument(Objects.isNull(userInfoRepository.getUserByVerifiedEmail(request.getEmail())),
                    ApplicationExceptionCode.EMAIL_HAD_BINDING_OTHERS);
            userInfo.setTempEmail(request.getEmail());
            mailClient.sendEmailActive(applicationProperties.getApplicationChineseName(), userInfo.getId(), request.getEmail());
            command.setEmail(request.getEmail());
            // 如果是更改了邮件，则重新设置状态为未认证
            if (!Objects.equals(request.getEmail(), userInfo.getEmail())) {
                command.setEmail("");
            }
        }
        userInfoRepository.completeUserInfo(command);
        return personalInfo();
    }

    @Override
    public PersonalInfoResponse personalInfo() {
        final UserInfo userInfo = userClient.currentUserInfo();
        return BeanCopierUtils.copy(userInfo, PersonalInfoResponse.class);
    }

    @Override
    public void modifyPassword(ModifyPasswordRequest request) {
        final UserInfo userInfo = userInfoRepository.getById(UserContextUtil.getLongUserId());
        if (Objects.isNull(userInfo)) {
            return;
        }
//        // 短信验证码校验
//        final SmsCodeVerifyRequest verifyRequest = SmsCodeVerifyRequest.builder()
//                .mobile(userInfo.getMobile())
//                .mobileCode(request.getVerifyCode())
//                .uuid(request.getUuid())
//                .build();
//        commonHelper.verifySmsCode(verifyRequest);
//        userInfo.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        userInfoMapper.updateById(userInfo);
    }
}
