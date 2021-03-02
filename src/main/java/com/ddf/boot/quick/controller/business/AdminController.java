package com.ddf.boot.quick.controller.business;

import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.UserContextUtil;
import com.ddf.boot.quick.biz.ICommonBizService;
import com.ddf.boot.quick.biz.ISysMenuBizService;
import com.ddf.boot.quick.biz.ISysRoleBizService;
import com.ddf.boot.quick.biz.ISysRoleMenuBizService;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.request.CaptchaRequest;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.request.ResetPasswordRequest;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.model.request.SysMenuUpdateRequest;
import com.ddf.boot.quick.model.request.SysRoleCreateRequest;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.model.request.SysRoleMenuBuildRoleMenuRequest;
import com.ddf.boot.quick.model.request.SysRolePageRequest;
import com.ddf.boot.quick.model.request.SysUserCreateRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.SysUserUpdateRequest;
import com.ddf.boot.quick.model.response.CaptchaResponse;
import com.ddf.boot.quick.model.response.LoginResponse;
import com.ddf.boot.quick.model.response.SysMenuTreeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>系统权限菜单用户管理相关</p >
 *
 * @menu 权限管理
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 14:23
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class AdminController {

    private final ISysMenuBizService sysMenuBizService;

    private final ISysRoleBizService sysRoleBizService;

    private final ICommonBizService commonBizService;

    private final ISysUserBizService sysUserBizService;

    private final ISysRoleMenuBizService sysRoleMenuBizService;


    /**
     * 生成验证码
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/generateCaptcha")
    public CaptchaResponse generateCaptcha(@RequestBody @Validated CaptchaRequest request) {
        return commonBizService.generateCaptcha(request);
    }

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/create")
    public SysUserDTO createSysUser(@RequestBody @Validated SysUserCreateRequest request) {
        return sysUserBizService.create(request);
    }

    /**
     * 使用密码登录
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/loginByPassword")
    public LoginResponse loginByPassword(@RequestBody @Validated LoginRequest request) {
        return sysUserBizService.loginByPassword(request);
    }


    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/pageList")
    public PageResult<SysUserDTO> sysUserPageList(@RequestBody @Validated SysUserPageRequest request) {
        return sysUserBizService.pageList(request);
    }

    /**
     * 更新系统用户信息
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/update")
    public SysUserDTO updateSysUser(@RequestBody @Validated SysUserUpdateRequest request) {
        return sysUserBizService.update(request);
    }

    /**
     * 重置系统用户密码
     *
     * @param request
     * @return
     */
    @PostMapping("sysUser/resetPassword")
    public Boolean resetPassword(@RequestBody @Validated ResetPasswordRequest request) {
        return sysUserBizService.resetPassword(request);
    }

    /**
     * 创建系统角色
     *
     * @param request
     * @return
     */
    @PostMapping("sysRole/saveOrUpdate")
    public SysRoleDTO saveOrUpdateSysRole(@RequestBody @Validated SysRoleCreateRequest request) {
        return sysRoleBizService.saveOrUpdate(request);
    }

    /**
     * 系统角色分页查询
     *
     * @param request
     * @return
     */
    @PostMapping("sysRole/pageList")
    public PageResult<SysRoleDTO> sysRolePageList(@RequestBody @Validated SysRolePageRequest request) {
        return sysRoleBizService.pageList(request);
    }


    /**
     * 创建系统菜单
     *
     * @param request
     * @return
     */
    @PostMapping("/sysMenu/create")
    public SysMenuDTO createSysMenu(@RequestBody @Validated SysMenuCreateRequest request) {
        return sysMenuBizService.create(request);
    }

    /**
     * 更新菜单
     *
     * @param request
     * @return
     */
    @PostMapping("sysMenu/update")
    public SysMenuDTO updateSysMenu(@RequestBody @Validated SysMenuUpdateRequest request) {
        return sysMenuBizService.update(request);
    }

    /**
     * 构建菜单树
     *
     * @return
     */
    @PostMapping("sysMenu/buildMenuTree")
    public List<SysMenuTreeResponse> buildMenuTree() {
        return sysMenuBizService.buildMenuTree();
    }

    /**
     * 构建角色授权树， 加载全部菜单树， 已授权的会用属性标识已被授权
     *
     * @param request
     * @return
     */
    @PostMapping("sysRoleMenu/buildRoleMenuTree")
    public List<SysMenuTreeResponse> buildRoleMenuTree(@RequestBody @Validated SysRoleMenuBuildRoleMenuRequest request) {
        return sysRoleMenuBizService.buildRoleMenuTree(request);
    }

    /**
     * 角色授权
     *
     * @param request
     * @return 返回授权菜单数量
     */
    @PostMapping("sysRoleMenu/authorization")
    public Integer authorization(@RequestBody @Validated SysRoleMenuAuthorizationRequest request) {
        return sysRoleMenuBizService.authorization(request);
    }

    /**
     * 构建用户的左侧菜单树，即用户有哪些菜单权限
     *
     * @return
     */
    @PostMapping("buildUserMenuTree")
    public List<SysMenuTreeResponse> buildUserMenuTree() {
        return sysRoleMenuBizService.buildUserMenuTree(UserContextUtil.getUserId());
    }
}
