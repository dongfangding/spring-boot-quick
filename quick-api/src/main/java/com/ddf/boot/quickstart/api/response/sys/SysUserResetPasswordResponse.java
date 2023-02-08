package com.ddf.boot.quickstart.api.response.sys;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/13 19:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserResetPasswordResponse implements Serializable {

    private static final long serialVersionUID = -1083988608168792084L;

    /**
     * 初始密码
     */
    private String initPassword;

    /**
     * 简单构建对象
     *
     * @param initPassword
     * @return
     */
    public static SysUserResetPasswordResponse of(String initPassword) {
        final SysUserResetPasswordResponse response = new SysUserResetPasswordResponse();
        response.setInitPassword(initPassword);
        return response;
    }
}
