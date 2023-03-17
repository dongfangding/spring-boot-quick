package com.ddf.boot.quickstart.core.strategy.login;


import com.ddf.boot.quickstart.api.enume.LoginTypeEnum;
import com.ddf.boot.quickstart.api.request.auth.LoginRequest;
import com.ddf.boot.quickstart.core.entity.UserInfo;

/**
 * <p>登录策略接口</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/23 23:38
 */
public interface LoginStrategy {

    /**
     * 登录类型
     * @return
     */
    LoginTypeEnum getLoginType();

    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    UserInfo login(LoginRequest loginRequest);

}
