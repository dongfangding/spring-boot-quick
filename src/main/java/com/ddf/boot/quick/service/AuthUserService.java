package com.ddf.boot.quick.service;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.common.mybatis.service.CustomizeIService;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.model.bo.AuthUserRegistryBo;
import com.ddf.boot.quick.model.bo.LoginRequest;
import com.ddf.boot.quick.model.vo.AuthUserVo;

/**
 * @author DDf on 2019/12/8
 */
public interface AuthUserService extends CustomizeIService<AuthUser> {


    /**
     * 用户注册
     *
     * @param authUserRegistryBo
     * @return
     */
    AuthUserVo registry(AuthUserRegistryBo authUserRegistryBo);


    /**
     * 登录
     *
     * @param loginRequest
     * @return
     */
    String loginByPassword(LoginRequest loginRequest);


    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    AuthUser findByName(String username);

    /**
     * 分页查询
     *
     * @param authUserPageBo
     * @return
     */
    PageResult<AuthUser> pageList(AuthUserPageBo authUserPageBo);

}
