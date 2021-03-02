package com.ddf.boot.quick.biz;

import com.ddf.boot.common.core.model.CommonSwitchRequest;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.request.ResetPasswordRequest;
import com.ddf.boot.quick.model.request.SysUserCreateRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.SysUserUpdateRequest;
import com.ddf.boot.quick.model.response.CurrentUserResponse;
import com.ddf.boot.quick.model.response.LoginResponse;

/**
 * 系统用户业务处理类
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 13:42
 */
public interface ISysUserBizService {

    /**
     * 获取当前用户详细信息
     *
     * @return
     */
    CurrentUserResponse currentUser();

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    SysUserDTO create(SysUserCreateRequest request);

    /**
     * 更新系统用户
     *
     * @param request
     * @return
     */
    SysUserDTO update(SysUserUpdateRequest request);

    /**
     * 系统用户登录
     *
     * @param request
     * @return
     */
    LoginResponse loginByPassword(LoginRequest request);

    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    PageResult<SysUserDTO> pageList(SysUserPageRequest request);

    /**
     * 启用禁用状态切换开关
     *
     * @param request
     * @return
     */
    Boolean activeSwitch(CommonSwitchRequest request);

    /**
     * 重置系统用户密码
     *
     * @param request
     * @return
     */
    Boolean resetPassword(ResetPasswordRequest request);
}
