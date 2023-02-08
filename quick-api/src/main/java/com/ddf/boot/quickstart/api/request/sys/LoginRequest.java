package com.ddf.boot.quickstart.api.request.sys;

import com.ddf.boot.common.api.model.captcha.request.CaptchaCheckRequest;
import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录请求参数
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 20:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    /**
     * 登录方式{@link LoginTypeEnum}
     */
    @NotNull
    private LoginTypeEnum loginType = LoginTypeEnum.PASSWORD;

    /**
     * 登录身份（用户名、手机号）
     */
    @NotBlank(message = "登录身份不能为空")
    private String loginIdentity;

    /**
     * 登录凭据（密码、验证码,TOKEN等）
     */
    @NotBlank(message = "登录凭据不能为空")
    private String credential;

    /**
     * 验证码对象，看情况是否需要
     */
    private CaptchaCheckRequest captchaVerifyRequest = new CaptchaCheckRequest();
}
