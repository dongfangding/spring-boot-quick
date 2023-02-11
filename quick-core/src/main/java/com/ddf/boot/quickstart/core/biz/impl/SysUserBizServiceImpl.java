package com.ddf.boot.quickstart.core.biz.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.api.model.common.CommonSwitchRequest;
import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.common.authentication.config.AuthenticationProperties;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.common.core.encode.BCryptPasswordEncoder;
import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.SecureUtil;
import com.ddf.boot.quickstart.api.dto.SysUserDTO;
import com.ddf.boot.quickstart.api.dto.SysUserRoleDTO;
import com.ddf.boot.quickstart.api.enume.SysUserStatusEnum;
import com.ddf.boot.quickstart.api.request.sys.ModifyPasswordRequest;
import com.ddf.boot.quickstart.api.request.sys.ResetPasswordRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserDetailRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserPageRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserRoleBatchInsertRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUpdatePasswordRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUpdateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysUserUploadAvatarRequest;
import com.ddf.boot.quickstart.api.response.ActiveSwitchResponse;
import com.ddf.boot.quickstart.api.response.sys.CurrentUserResponse;
import com.ddf.boot.quickstart.api.response.sys.SysUserDetailResponse;
import com.ddf.boot.quickstart.api.response.sys.SysUserResetPasswordResponse;
import com.ddf.boot.quickstart.core.biz.ISysUserBizService;
import com.ddf.boot.quickstart.core.common.exception.BizCode;
import com.ddf.boot.quickstart.core.convert.SysUserConverter;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.helper.SysUserHelper;
import com.ddf.boot.quickstart.core.service.ISysUserRoleService;
import com.ddf.boot.quickstart.core.service.ISysUserService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统用户业务类实现
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 14:45
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysUserBizServiceImpl implements ISysUserBizService {

    private final ISysUserService sysUserService;
    private final ISysUserRoleService sysUserRoleService;
    private final SysUserHelper sysUserHelper;
    private final AuthenticationProperties authenticationProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 获取当前用户详细信息
     *
     * @return
     */
    @Override
    public CurrentUserResponse currentUser() {
        final SysUser user = sysUserHelper.getCurrentSysUser();
        final CurrentUserResponse response = new CurrentUserResponse();
        if (Objects.nonNull(user)) {
            response.setBaseInfo(SysUserConverter.INSTANCE.convert(user));
            final List<SysUserRoleDTO> roles = sysUserRoleService.getUserActiveRoleList(user.getUserId());
            response.setRoles(roles);
            response.setAdmin(roles.stream().anyMatch(val -> Objects.equals(val.getIsAdmin(), CommonLogic.TRUE.getLogic())));
        }
        return response;
    }

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO create(SysUserCreateRequest request) {
        // 重复性判断
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByLoginName(request.getLoginName())),
                BizCode.LOGIN_NAME_REPEAT
        );
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByNickname(request.getNickname())),
                BizCode.NICK_NAME_REPEAT
        );
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByMobile(request.getMobile())),
                BizCode.MOBILE_REPEAT
        );

        String userId = IdUtil.getSnowflakeNextIdStr();
        SysUser sysUser = SysUserConverter.INSTANCE.requestConvert(request);
        sysUser.setUserId(userId);
        sysUser.setPassword(bCryptPasswordEncoder.encode(authenticationProperties.getBiz().getResetPassword()));
        sysUserService.save(sysUser);

        // 处理用户关联角色
        relativeUserRole(userId, request.getRoleIdList());
        return SysUserConverter.INSTANCE.convert(sysUserService.getByPrimaryKey(sysUser.getId()));
    }

    /**
     * 更新系统用户
     *
     * @param request
     * @return
     */
    @Override
    public SysUserDTO update(SysUserUpdateRequest request) {
        final SysUser sysUser = sysUserService.getByPrimaryKey(request.getId());
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.SYS_USER_RECORD_NOT_EXIST);
        SysUser searchSysUser = sysUserService.getByLoginName(request.getLoginName());
        if (Objects.nonNull(searchSysUser)) {
            PreconditionUtil.checkArgument(
                    Objects.equals(searchSysUser.getId(), request.getId()), BizCode.LOGIN_NAME_REPEAT);
        }
        searchSysUser = sysUserService.getByNickname(request.getNickname());
        if (Objects.nonNull(searchSysUser)) {
            PreconditionUtil.checkArgument(
                    Objects.equals(searchSysUser.getId(), request.getId()), BizCode.NICK_NAME_REPEAT);
        }
        searchSysUser = sysUserService.getByMobile(request.getMobile());
        if (Objects.nonNull(searchSysUser)) {
            PreconditionUtil.checkArgument(
                    Objects.equals(searchSysUser.getId(), request.getId()), BizCode.MOBILE_REPEAT);
        }
        final SysUser updateUser = SysUserConverter.INSTANCE.updateConvert(request);
        updateUser.setVersion(sysUser.getVersion());
        sysUserService.update(updateUser);

        // 处理用户关联角色
        relativeUserRole(sysUser.getUserId(), request.getRoleIdList());
        return SysUserConverter.INSTANCE.convert(sysUserService.getByPrimaryKey(request.getId()));
    }


    /**
     * 关联用户角色
     *
     * @param userId
     * @param roleIdList
     */
    private void relativeUserRole(String userId, Set<Long> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList) || StrUtil.isBlank(userId)) {
            return;
        }
        // 清除旧的关联数据
        sysUserRoleService.deleteUserRole(userId);
        // 重新关联
        SysUserRoleBatchInsertRequest sysUserRoleBatchInsertRequest = SysUserRoleBatchInsertRequest.builder()
                .userId(userId)
                .roleIdList(roleIdList)
                .build();
        sysUserRoleService.batchRelativeUser(sysUserRoleBatchInsertRequest);
    }

    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysUserDTO> pageList(SysUserPageRequest request) {
        final PageResult<SysUser> result = sysUserService.pageList(request);
        if (result.isEmpty()) {
            return PageUtil.empty(request);
        }
        final PageResult<SysUserDTO> responsePageResult = PageUtil.convertPageResult(
                result, SysUserConverter.INSTANCE::convert);
        final List<SysUserDTO> content = responsePageResult.getContent();
        final Map<String, SysUser> sysUserMap = sysUserHelper.getUserMap(content);

        SysUser sysUser;
        for (SysUserDTO dto : content) {
            sysUser = sysUserMap.get(dto.getCreateBy());
            if (Objects.nonNull(sysUser)) {
                dto.setCreateByName(sysUser.getLoginName());
            }
            sysUser = sysUserMap.get(dto.getModifyBy());
            if (Objects.nonNull(sysUser)) {
                dto.setModifyByName(sysUser.getLoginName());
            }
        }
        return responsePageResult;
    }


    /**
     * 启用禁用状态切换开关
     *
     * @param request
     * @return
     */
    @Override
    public ActiveSwitchResponse activeSwitch(CommonSwitchRequest request) {
        final SysUser sysUser = sysUserService.getByPrimaryKey(request.getId());
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.ROLE_RECORD_NOT_EXIST);

        // 要更新的状态
        SysUserStatusEnum targetStatus;
        if (Objects.equals(CommonLogic.TRUE.getLogic(), request.getSwitchFlag())) {
            targetStatus = SysUserStatusEnum.ACTIVE;
        } else {
            targetStatus = SysUserStatusEnum.DISABLE;
        }
        if (Objects.equals(targetStatus.getCode(), sysUser.getStatus())) {
            return ActiveSwitchResponse.of(targetStatus.getCode());
        }
        sysUser.setStatus(targetStatus.getCode());
        sysUserService.update(sysUser);
        return ActiveSwitchResponse.of(targetStatus.getCode());
    }

    /**
     * 重置系统用户密码
     *
     * @param request
     * @return
     */
    @Override
    public SysUserResetPasswordResponse resetPassword(ResetPasswordRequest request) {
        final SysUser sysUser = sysUserService.getByPrimaryKey(request.getId());
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.SYS_USER_RECORD_NOT_EXIST);
        PreconditionUtil.checkArgument(sysUserHelper.isAdmin(), BizCode.NOT_SUPER_ADMIN);
        final String initPassword = authenticationProperties.getBiz().getResetPassword();
        sysUser.setPassword(SecureUtil.signWithHMac(initPassword, sysUser.getUserId()));
        log.info("重置用户[{}]密码为[{}]", sysUser.getUserId(), initPassword);
        sysUserService.update(sysUser);
        return SysUserResetPasswordResponse.of(initPassword);
    }


    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @Override
    public Boolean updatePassword(SysUserUpdatePasswordRequest request) {
        final SysUser user = sysUserHelper.getCurrentSysUser();
        PreconditionUtil.checkArgument(
                Objects.equals(user.getPassword(), SecureUtil.signWithHMac(request.getOldPassword(), user.getUserId())),
                BizCode.LOGIN_PASSWORD_ERROR
        );
        user.setPassword(SecureUtil.signWithHMac(request.getNewPassword(), user.getUserId()));
        return sysUserService.update(user);
    }

    /**
     * 上传用户头像
     *
     * @param request
     * @return
     */
    @Override
    public Boolean uploadAvatar(SysUserUploadAvatarRequest request) {
        final SysUser user = sysUserHelper.getCurrentSysUser();
        user.setAvatarUrl(request.getAvatarUrl());
        return sysUserService.update(user);
    }

    /**
     * 查询用户详情
     *
     * @param request
     * @return
     */
    @Override
    public SysUserDetailResponse detail(SysUserDetailRequest request) {
        final SysUser sysUser = sysUserService.getByUserId(request.getUserId());
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.SYS_USER_RECORD_NOT_EXIST);
        final SysUserDetailResponse response = SysUserConverter.INSTANCE.convertDetailResponse(sysUser);
        response.setRoleList(sysUserRoleService.getUserActiveRoleList(request.getUserId()));
        return response;
    }

    @Override
    public void modifyPassword(ModifyPasswordRequest request) {
        final SysUser sysUser = sysUserService.getByUserId(UserContextUtil.getUserId());
        sysUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        sysUserService.save(sysUser);
    }
}
