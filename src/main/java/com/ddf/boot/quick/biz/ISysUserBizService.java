package com.ddf.boot.quick.biz;

import com.ddf.boot.quick.model.request.CreateSysUserRequest;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.response.CreateSysUserResponse;
import com.ddf.boot.quick.model.response.LoginResponse;

/**
 * 系统用户业务处理类
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:42
 */
public interface ISysUserBizService {

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    CreateSysUserResponse create(CreateSysUserRequest request);

    /**
     * 系统用户登录
     *
     * @param request
     * @return
     */
    LoginResponse loginByPassword(LoginRequest request);
}
