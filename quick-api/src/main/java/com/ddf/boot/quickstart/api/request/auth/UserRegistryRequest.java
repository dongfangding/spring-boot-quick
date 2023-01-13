package com.ddf.boot.quickstart.api.request.auth;

import com.ddf.boot.common.api.validator.constraint.Mobile;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>用户注册请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 20:59
 */
@Data
public class UserRegistryRequest implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Mobile
    private String mobile;

    /**
     * 验证码接口返回的uuid， 需要回传
     */
    @NotBlank(message = "手机验证码uuid不能为空")
    private String uuid;

    /**
     * 手机验证码
     */
    @NotBlank(message = "手机验证码不能为空")
    private String mobileCode;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;
}
