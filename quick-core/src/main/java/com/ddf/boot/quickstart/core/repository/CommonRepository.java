package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.dto.EmailToken;

/**
 * <p>通用的或者一些边缘业务的仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/28 15:20
 */
public interface CommonRepository {

    /**
     * 设置短信验证码
     *
     * @param mobile
     * @param uuid
     * @param randomCode
     * @return
     */
    void setSmsCode(String mobile, String uuid, String randomCode);

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param uuid
     * @return
     */
    String getSmsCode(String mobile, String uuid);

    /**
     * 设置邮箱激活token
     *
     * @param email
     * @param token
     * @return
     */
    void setEmailActiveToken(String email, String token, Long userId);

    /**
     * 根据邮箱激活token获取对应用户id和邮箱
     *
     * @param token
     * @return
     */
    EmailToken getEmailActiveToken(String token);
}
