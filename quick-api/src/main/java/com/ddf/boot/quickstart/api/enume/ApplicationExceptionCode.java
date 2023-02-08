package com.ddf.boot.quickstart.api.enume;

import com.ddf.boot.common.api.exception.BaseCallbackCode;

/**
 * <p>错误枚举</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:07
 */
public enum ApplicationExceptionCode implements BaseCallbackCode {

    /**
     * 错误枚举
     */
    MOBILE_IS_USED("mobile_is_used", "手机号已被使用"),
    VERIFY_CODE_EXPIRED("verify_code_expired", "验证码已过期"),
    VERIFY_CODE_NOT_MATCH("verify_code_not_match", "验证码不匹配"),
    SMS_CODE_LIMIT("SMS_CODE_LIMIT", "今日短信发送额度已用完，无法继续使用~"),
    MOBILE_NOT_REGISTERED("mobile_not_registered", "手机号尚未注册~"),
    EMAIL_HAD_BINDING_OTHERS("EMAIL_HAD_BINDING_OTHERS", "该邮箱已被其他用户绑定~"),
    LOGIN_PASSWORD_ERROR("login_password_error", "密码不匹配，请确认~"),
    LOGIN_STRATEGY_MAPPING_ERROR("login_strategy_mapping_error", "登录策略异常~", "服务器开小差了~"),
    EMAIL_ACTIVE_TOKEN_EXPIRED("email_active_token_expired", "激活链接已过期，请重新验证"),
    USER_NOT_EXIST("user_not_exist", "用户不存在"),
    ACCOUNT_EXISTS("ACCOUNT_EXISTS", "账号已存在"),
    PASSWORD_ERROR("PASSWORD_ERROR", "密码错误"),
    ACCOUNT_NOT_EXISTS("ACCOUNT_NOT_EXISTS", "账号不存在"),
    ACCOUNT_IN_BLACK("ACCOUNT_IN_BLACK", "账号已被封禁"),
    IDENTITY_MISMATCH("IDENTITY_MISMATCH", "身份不匹配~"),
    ROLE_NAME_EXIST("ROLE_NAME_EXIST", "角色名称已经存在"),
    ROLE_RECORD_NOT_EXIST("ROLE_RECORD_NOT_EXIST", "角色记录不存在"),
    SYS_USER_RECORD_NOT_EXIST("SYS_USER_RECORD_NOT_EXIST", "系统用户记录不存在"),
    NOT_SUPER_ADMIN("NOT_SUPER_ADMIN", "非超级管理员不可执行操作"),
    MENU_NAME_REPEAT("MENU_NAME_REPEAT", "菜单名称已存在"),
    MENU_RECORD_NOT_EXIST("MENU_RECORD_NOT_EXIST", "菜单记录不存在"),
    CONFIG_CODE_REPEAT("CONFIG_CODE_REPEAT", "配置代码已存在"),
    ;

    private final String code;

    private final String desc;

    private final String bizMessage;

    ApplicationExceptionCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
        this.bizMessage = desc;
    }

    ApplicationExceptionCode(String code, String desc, String bizMessage) {
        this.code = code;
        this.desc = desc;
        this.bizMessage = bizMessage;
    }


    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }

    @Override
    public String getBizMessage() {
        return bizMessage;
    }
}
