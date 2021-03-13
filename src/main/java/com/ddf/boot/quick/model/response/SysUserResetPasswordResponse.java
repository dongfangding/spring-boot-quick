package com.ddf.boot.quick.model.response;

import lombok.Data;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/13 19:14
 */
@Data
public class SysUserResetPasswordResponse {

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
