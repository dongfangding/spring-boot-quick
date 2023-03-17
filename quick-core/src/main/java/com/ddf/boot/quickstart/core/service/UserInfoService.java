package com.ddf.boot.quickstart.core.service;

import com.ddf.boot.quickstart.core.entity.UserInfo;

/**
 * <p>用户信息</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/15 20:54
 */
public interface UserInfoService {

    /**
     * 插入注册用户信息
     *
     * @param mobile
     * @param password
     * @param avatarUrl 默认头像地址
     * @return
     */
    UserInfo registerUserInfo(String mobile, String password, String avatarUrl);
}
