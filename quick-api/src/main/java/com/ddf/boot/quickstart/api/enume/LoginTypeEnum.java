package com.ddf.boot.quickstart.api.enume;

import java.util.Objects;

/**
 * <p>登录类型</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/23 23:24
 */
public enum LoginTypeEnum {

    /**
     * 用户名密码登录
     */
    PASSWORD,

    /**
     * 手机验证码登录
     */
    SMS_CODE,

    /**
     * TOKEN登录
     */
    TOKEN

    ;

    /**
     * 当前登录方式是否需要生成token
     *
     * @return
     */
    public boolean shouldCreateToken() {
        return !Objects.equals(this, TOKEN);
    }
}
