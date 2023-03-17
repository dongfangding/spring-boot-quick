package com.ddf.boot.quickstart.api.request.auth;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * <p>修改密码请求</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/09/06 18:03
 */
@Data
public class ModifyPasswordRequest implements Serializable {

    private static final long serialVersionUID = 1516322558409231083L;

    /**
     * 随机数， 获取验证码时返回的的唯一标识
     */
    @NotBlank(message = "uuid不能为空")
    private String uuid;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;

    /**
     * 最新密码
     */
    @NotBlank(message = "最新密码不能为空")
    private String newPassword;
}
