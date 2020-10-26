package com.ddf.boot.quick.controller;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.model.datao.quick.AuthUser;
import com.ddf.boot.quick.model.bo.AuthUserPageBo;
import com.ddf.boot.quick.model.bo.AuthUserRegistryBo;
import com.ddf.boot.quick.model.bo.LoginBo;
import com.ddf.boot.quick.model.vo.AuthUserVo;
import com.ddf.boot.quick.service.AuthUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * $
 *
 * @author dongfang.ding
 * @date 2019/12/9 0009 13:36
 */
@RestController
@RequestMapping("authUser")
@Api(value = "用户控制器", tags = {"用户控制器"})
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;


    /**
     * 用户注册
     *
     * @param authUserRegistryBo
     * @return com.ddf.boot.quick.model.vo.AuthUserVo
     * @author dongfang.ding
     * @date 2019/12/9 0009 20:07
     **/
    @PostMapping("/registry")
    @ApiOperation(value = "用户注册")
    public AuthUserVo registry(@RequestBody @Validated @ApiParam(value = "注册请求参数", required = true) AuthUserRegistryBo authUserRegistryBo) {
        return authUserService.registry(authUserRegistryBo);
    }

    /**
     * 用户登录
     *
     * @param loginBo
     * @return java.lang.String
     * @author dongfang.ding
     * @date 2019/12/9 0009 20:09
     **/
    @PostMapping("loginByPassword")
    @ApiOperation("用户登录")
    public String loginByPassword(@RequestBody @Validated @ApiParam(value = "登录参数", required = true) LoginBo loginBo) {
        return authUserService.loginByPassword(loginBo);
    }

    /**
     * 分页查询
     *
     * @param authUserPageBo
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.ddf.boot.common.model.datao.quick.AuthUser>
     * @author dongfang.ding
     * @date 2019/12/9 0009 21:33
     **/
    @PostMapping("pageList")
    @ApiOperation("分页查询用户列表")
    public PageResult<AuthUser> pageList(@RequestBody @ApiParam(value = "查询对象参数") AuthUserPageBo authUserPageBo) {
        return authUserService.pageList(authUserPageBo);
    }
}
