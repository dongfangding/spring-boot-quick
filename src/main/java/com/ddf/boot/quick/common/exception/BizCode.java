package com.ddf.boot.quick.common.exception;

import com.ddf.boot.common.core.exception200.BaseCallbackCode;
import lombok.Getter;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/13 16:59
 */
public enum BizCode implements BaseCallbackCode {
    /**
     * OSS异常
     */
    OSS_ERROR("10000", "OSS异常"),

    LOGIN_NAME_REPEAT("10001", "登录名已存在"),
    NICK_NAME_REPEAT("10002", "昵称已存在"),
    MOBILE_REPEAT("10003", "手机号已存在"),
    VERIFY_CODE_EXPIRED("10004", "验证码已过期"),
    VERIFY_CODE_NOT_MAPPING("10005", "验证码错误"),
    LOGIN_NAME_NOT_EXIST("10006", "登录名不存在"),
    LOGIN_PASSWORD_ERROR("10007", "登录密码错误"),
    ROLE_NAME_EXIST("10008", "角色名称已经存在"),
    ROLE_RECORD_NOT_EXIST("10008", "角色记录不存在"),


    ;


    BizCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Getter
    private final String code;

    @Getter
    private final String description;
}
