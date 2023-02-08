package com.ddf.boot.quickstart.core.biz;



import com.ddf.boot.common.api.model.common.CommonSwitchRequest;
import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.quickstart.api.dto.SysUserDTO;
import com.ddf.boot.quickstart.api.request.sys.ResetPasswordRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserDetailRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserPageRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUpdatePasswordRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUpdateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUploadAvatarRequest;
import com.ddf.boot.quickstart.api.response.ActiveSwitchResponse;
import com.ddf.boot.quickstart.api.response.sys.CurrentUserResponse;
import com.ddf.boot.quickstart.api.response.sys.SysUserDetailResponse;
import com.ddf.boot.quickstart.api.response.sys.SysUserResetPasswordResponse;

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
    ActiveSwitchResponse activeSwitch(CommonSwitchRequest request);

    /**
     * 重置系统用户密码
     *
     * @param request
     * @return
     */
    SysUserResetPasswordResponse resetPassword(ResetPasswordRequest request);

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    Boolean updatePassword(SysUserUpdatePasswordRequest request);

    /**
     * 上传用户头像
     *
     * @param request
     * @return
     */
    Boolean uploadAvatar(SysUserUploadAvatarRequest request);

    /**
     * 查询用户详情
     *
     * @param request
     * @return
     */
    SysUserDetailResponse detail(SysUserDetailRequest request);
}
