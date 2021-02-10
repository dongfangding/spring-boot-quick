package com.ddf.boot.quick.model.bo;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录请求参数
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 20:03
 */
@Data
@Accessors(chain = true)
public class LoginRequest {

    /**
     * 登录名
     */
    @NotEmpty(message = "姓名不能为空")
    private String loginName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    @NotEmpty(message = "验证码不能为空")
    private String verifyCode;
}
