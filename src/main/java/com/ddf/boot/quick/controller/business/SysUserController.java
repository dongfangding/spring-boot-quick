package com.ddf.boot.quick.controller.business;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.biz.ICommonBizService;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.request.CaptchaRequest;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.request.ResetPasswordRequest;
import com.ddf.boot.quick.model.request.SysUserCreateRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.SysUserUpdateRequest;
import com.ddf.boot.quick.model.response.CaptchaResponse;
import com.ddf.boot.quick.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @menu 权限管理
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@RestController
@RequestMapping("/sysUser")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserController {

    private final ICommonBizService commonBizService;

    private final ISysUserBizService sysUserBizService;


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @PostMapping("generateCaptcha")
    public CaptchaResponse generateCaptcha(@RequestBody @Validated CaptchaRequest request) {
        return commonBizService.generateCaptcha(request);
    }

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @PostMapping("create")
    public SysUserDTO create(@RequestBody @Validated SysUserCreateRequest request) {
        return sysUserBizService.create(request);
    }

    /**
     * 使用密码登录
     *
     * @param request
     * @return
     */
    @PostMapping("loginByPassword")
    public LoginResponse loginByPassword(@RequestBody @Validated LoginRequest request) {
        return sysUserBizService.loginByPassword(request);
    }


    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("pageList")
    public PageResult<SysUserDTO> pageList(@RequestBody @Validated SysUserPageRequest request) {
        return sysUserBizService.pageList(request);
    }

    /**
     * 更新系统用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("update")
    public SysUserDTO update(@RequestBody @Validated SysUserUpdateRequest request) {
        return sysUserBizService.update(request);
    }

    /**
     * 重置系统用户密码
     *
     * @param request
     * @return
     */
    @PostMapping("resetPassword")
    public Boolean resetPassword(@RequestBody @Validated ResetPasswordRequest request) {
        return sysUserBizService.resetPassword(request);
    }
}

