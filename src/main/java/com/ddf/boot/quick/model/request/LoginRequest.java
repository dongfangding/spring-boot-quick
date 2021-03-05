package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "登录名不能为空")
    @Size(min = 1, max = 32, message = "登录名长度必须位于1到32个字符之间")
    private String loginName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 1, max = 32, message = "密码长度必须位于1到32个字符之间")
    private String password;

    /**
     * 验证码接口返回的tokenId
     */
    @NotBlank(message = "验证码随机数不能为空")
    private String tokenId;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
