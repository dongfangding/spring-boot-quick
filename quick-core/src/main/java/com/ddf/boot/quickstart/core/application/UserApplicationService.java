package com.ddf.boot.quickstart.core.application;

import com.ddf.boot.quickstart.api.request.sys.ModifyPasswordRequest;
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
     * 心跳
     * @param userId
     */
    void heartBeat(Long userId);


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
