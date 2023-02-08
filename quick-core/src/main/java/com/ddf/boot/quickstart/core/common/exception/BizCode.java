package com.ddf.boot.quickstart.core.common.exception;

import com.ddf.boot.common.api.exception.BaseCallbackCode;
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
    SYS_USER_RECORD_NOT_EXIST("10008", "系统用户记录不存在"),
    NOT_SUPER_ADMIN("10009", "非超级管理员不可执行操作"),
    MENU_NAME_REPEAT("10010", "菜单名称已存在"),
    MENU_RECORD_NOT_EXIST("10011", "菜单记录不存在"),
    CONFIG_CODE_REPEAT("10012", "配置代码已存在"),
    SYS_USER_DISABLE("10013", "用户已被禁用"),
    TEST_SIMPLE_BIZ_MESSAGE("10014", "基本异常"),
    TEST_BIZ_MESSAGE("10015", "boot-quick演示业务异常", "啦啦啦啦，请重试"),
    TEST_FILL_EXCEPTION("10016", "带占位符的异常演示[{0}]"),
    TEST_FILL_BIZ_EXCEPTION("10017", "带占位符的异常演示[{0}],客户端隐藏详细信息", "报错啦")

    ;


    BizCode(String code, String description) {
        this.code = code;
        this.description = description;
        this.bizMessage = description;
    }

    BizCode(String code, String description, String bizMessage) {
        this.code = code;
        this.description = description;
        this.bizMessage = bizMessage;
    }

    @Getter
    private final String code;

    @Getter
    private final String description;

    @Getter
    private final String bizMessage;

}
