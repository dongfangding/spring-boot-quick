package com.ddf.boot.quickstart.core.application;

import com.ddf.boot.quickstart.api.request.auth.ModifyPasswordRequest;
import com.ddf.boot.quickstart.api.request.auth.UserRegistryRequest;
import com.ddf.boot.quickstart.api.request.user.CompleteUserInfoRequest;
import com.ddf.boot.quickstart.api.response.user.PersonalInfoResponse;

/**
 * <p>用户应用层</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:39
 */
public interface UserApplicationService {

    /**
     * 注册账号
     *
     * @param request
     */
    void registry(UserRegistryRequest request);

    /**
     * 完善用户信息
     *
     * @param request
     */
    PersonalInfoResponse completeInfo(CompleteUserInfoRequest request);

    /**
     * 个人中心
     *
     * @return
     */
    PersonalInfoResponse personalInfo();

    /**
     * 修改密码
     *
     * @param request
     */
    void modifyPassword(ModifyPasswordRequest request);

}
