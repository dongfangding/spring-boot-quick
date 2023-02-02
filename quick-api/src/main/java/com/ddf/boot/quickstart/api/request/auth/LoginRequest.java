package com.ddf.boot.quickstart.api.request.auth;

import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>登录请求</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/23 23:25
 */
@Data
public class LoginRequest implements Serializable {

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
     * 随机数， 验证码登录时需要
     */
    private String uuid;
}
