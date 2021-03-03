package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * <p>修改密码</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 21:37
 */
@Data
public class SysUserUpdatePasswordRequest {

    /**
     * 旧密码
     *
     */
    @NotEmpty(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

}
