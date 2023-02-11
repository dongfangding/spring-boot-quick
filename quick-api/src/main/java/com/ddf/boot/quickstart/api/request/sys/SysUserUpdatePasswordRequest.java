package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>修改密码</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/03 21:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserUpdatePasswordRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 旧密码
     *
     */
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;

}
