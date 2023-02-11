package com.ddf.boot.quickstart.core.helper;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.common.api.model.captcha.request.CaptchaRequest;
import com.ddf.boot.common.api.model.captcha.response.ApplicationCaptchaResult;
import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.common.authentication.config.AuthenticationProperties;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.common.ext.sms.model.SmsSendRequest;
import com.ddf.boot.common.ext.sms.model.SmsSendResponse;
import com.ddf.boot.common.redis.helper.RedisTemplateHelper;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.dto.EmailToken;
import com.ddf.boot.quickstart.api.enume.ApplicationExceptionCode;
import com.ddf.boot.quickstart.api.request.common.SendSmsCodeRequest;
import com.ddf.boot.quickstart.api.request.common.SmsCodeVerifyRequest;
import com.ddf.boot.quickstart.api.response.common.ApplicationSmsSendResponse;
import com.ddf.boot.quickstart.core.client.SmsClient;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.repository.CommonRepository;
import com.ddf.boot.quickstart.core.service.ISysUserService;
import com.ddf.common.captcha.helper.CaptchaHelper;
import java.util.Date;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
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

    private final CaptchaHelper captchaHelper;
    private final AuthenticationProperties authenticationProperties;
    private final RedisTemplateHelper redisTemplateHelper;
    private final CommonRepository commonRepository;
    private final ApplicationProperties applicationProperties;
    private final SmsClient smsClient;
    private final ISysUserService sysUserService;

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
        captchaHelper.check(CaptchaCheckRequest.builder()
                .uuid(request.getUuid())
                .verification(request.isVerification())
                .captchaVerification(request.getCaptchaVerification())
                .captchaType(request.getCaptchaType())
                .verifyCode(request.getVerifyCode())
                .build());
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public SmsSendResponse sendSmsCodeWithoutAnyLimit(String mobile) {
        final SmsSendRequest request = new SmsSendRequest();
        request.setPhoneNumbers(mobile);
        return smsClient.send(request);
    }

    /**
     * 发送注册短信验证码，并返回绑定的tokenId（随机参数，客户端回传进行表单绑定），有限流
     *
     * @param sendSmsCodeRequest
     * @return
     */
    public ApplicationSmsSendResponse sendAndLoadRegisterSmsCodeWithLimit(SendSmsCodeRequest sendSmsCodeRequest) {
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByMobile(sendSmsCodeRequest.getMobile())),
                ApplicationExceptionCode.MOBILE_IS_USED);
        return sendAndLoadSmsCodeWithLimit(sendSmsCodeRequest);
    }

    /**
     * 发送并存储短信验证码，并返回绑定的tokenId（随机参数，客户端回传进行表单绑定），有限流
     *
     * @param sendSmsCodeRequest
     * @return
     */
    public ApplicationSmsSendResponse sendAndLoadSmsCodeWithLimit(SendSmsCodeRequest sendSmsCodeRequest) {
        if (applicationProperties.isSmsCodeMockEnabled()) {
            return sendAndLoadSmsCode(sendSmsCodeRequest.getMobile());
        }
        // 验证码校验
        final CaptchaCheckRequest captchaVerifyRequest = sendSmsCodeRequest.getCaptchaVerifyRequest();
        captchaVerifyRequest.setVerification(true);
        verifyCaptcha(captchaVerifyRequest);
        final String uid = StrUtil.blankToDefault(UserContextUtil.getUserId(), UserContextUtil.getRequestContext()
                .getImei());
        return redisTemplateHelper.sliderWindowAccessExpiredAtCheckException(
                RedisKeyEnum.SMS_RATE_LIMIT_KEY.getKey(uid),
                applicationProperties.getSmsDailyLimit(), DateUtils.getEndOfDay(new Date()), () -> {
            return sendAndLoadSmsCode(sendSmsCodeRequest.getMobile());
        }, ApplicationExceptionCode.SMS_CODE_LIMIT);
    }

    /**
     * 发送并存储短信验证码，并返回绑定的tokenId（随机参数，客户端回传进行表单绑定）， 无限流
     *
     * @param mobile
     * @return
     */
    public ApplicationSmsSendResponse sendAndLoadSmsCode(String mobile) {
        final SmsSendResponse tempResponse = sendSmsCodeWithoutAnyLimit(mobile);
        final String uuid = RandomUtil.randomString(16);
        commonRepository.setSmsCode(mobile, uuid, tempResponse.getRandomCode());
        return ApplicationSmsSendResponse.builder()
                .uuid(uuid)
                .build();
    }

    /**
     * 校验短信验证码， 可使用白名单功能
     *
     * @param request
     * @return
     */
    public void verifySmsCode(SmsCodeVerifyRequest request) {
        PreconditionUtil.requiredParamCheck(request);
        String mobile = request.getMobile();
        // 验证码白名单忽略处理
        if (Objects.isNull(authenticationProperties) || CollectionUtil.isEmpty(authenticationProperties.getBiz().getWhiteLoginNameList())
                || !authenticationProperties.getBiz().getWhiteLoginNameList().contains(request.getMobile())) {
            // 校验验证码
            String verifyCode = commonRepository.getSmsCode(mobile, request.getUuid());
            PreconditionUtil.checkArgument(StrUtil.isNotBlank(verifyCode), ApplicationExceptionCode.VERIFY_CODE_EXPIRED);
            PreconditionUtil.checkArgument(StrUtil.equals(verifyCode, request.getMobileCode()), ApplicationExceptionCode.VERIFY_CODE_NOT_MATCH);
        }
    }

    /**
     * 验证邮箱链接激活
     *
     * @param response
     * @param token
     */
    public void verifyEmailActiveToken(HttpServletResponse response, String token) {
        final EmailToken emailToken = commonRepository.getEmailActiveToken(token);
        PreconditionUtil.checkArgument(Objects.nonNull(emailToken), ApplicationExceptionCode.EMAIL_ACTIVE_TOKEN_EXPIRED);

        final String userId = emailToken.getUserId();
        final String email = emailToken.getEmail();
        final SysUser sysUser = sysUserService.getByUserId(userId);
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), ApplicationExceptionCode.USER_NOT_EXIST);
        sysUser.setEmail(email);
        final boolean bool = sysUserService.save(sysUser);
        response.setContentType("text/html;charset=utf-8");
        if (bool) {
            WebUtil.responseSuccess(response, "验证成功");
        } else {
            WebUtil.responseError(response, 400, "验证失败，链接可能已过期或邮箱已验证通过~");
        }
    }
}
