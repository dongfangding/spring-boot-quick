package com.ddf.boot.quick.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.authentication.config.AuthenticationProperties;
import com.ddf.boot.common.authentication.model.AuthenticateToken;
import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.common.authentication.util.TokenUtil;
import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.common.core.model.CommonSwitchRequest;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.DateUtils;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.SecureUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.common.rocketmq.dto.RocketMQDestination;
import com.ddf.boot.common.rocketmq.helper.RocketMQHelper;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.common.exception.BizCode;
import com.ddf.boot.quick.common.redis.CacheKeys;
import com.ddf.boot.quick.constants.RocketMQConstants;
import com.ddf.boot.quick.constants.enumration.SysUserStatusEnum;
import com.ddf.boot.quick.converter.mapper.SysUserConverterMapper;
import com.ddf.boot.quick.event.SysUserLoginEvent;
import com.ddf.boot.quick.helper.SysUserHelper;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.dto.SysUserRoleDTO;
import com.ddf.boot.quick.model.dto.UserLoginHistoryDTO;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.request.ResetPasswordRequest;
import com.ddf.boot.quick.model.request.SysUserCreateRequest;
import com.ddf.boot.quick.model.request.SysUserDetailRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.SysUserRoleBatchInsertRequest;
import com.ddf.boot.quick.model.request.SysUserUpdatePasswordRequest;
import com.ddf.boot.quick.model.request.SysUserUpdateRequest;
import com.ddf.boot.quick.model.request.SysUserUploadAvatarRequest;
import com.ddf.boot.quick.model.response.ActiveSwitchResponse;
import com.ddf.boot.quick.model.response.CurrentUserResponse;
import com.ddf.boot.quick.model.response.LoginResponse;
import com.ddf.boot.quick.model.response.SysUserDetailResponse;
import com.ddf.boot.quick.model.response.SysUserResetPasswordResponse;
import com.ddf.boot.quick.service.ISysUserRoleService;
import com.ddf.boot.quick.service.ISysUserService;
import com.ddf.common.ids.service.api.IdsApi;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
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

    private final IdsApi idsApi;

    private final StringRedisTemplate stringRedisTemplate;

    private final ApplicationContext applicationContext;

    private final SysUserHelper sysUserHelper;

    private final AuthenticationProperties authenticationProperties;

    private final RocketMQHelper rocketMQHelper;

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
            response.setBaseInfo(SysUserConverterMapper.INSTANCE.convert(user));
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

        String userId = idsApi.getSnowflakeId();
        SysUser sysUser = SysUserConverterMapper.INSTANCE.requestConvert(request);
        sysUser.setUserId(userId);
        sysUser.setPassword(SecureUtil.signWithHMac(request.getPassword(), userId));
        sysUserService.save(sysUser);

        // 处理用户关联角色
        relativeUserRole(userId, request.getRoleIdList());

        // 发送创建admin-user mq消息
        rocketMQHelper.syncSend(RocketMQDestination.builder()
                .topic(RocketMQConstants.Topic.GLOBAL_TOPIC)
                .tags(RocketMQConstants.Tags.TAG_ADMIN_CREATE_USER)
                .build(), SysUserConverterMapper.INSTANCE.convertToEsUser(sysUser));

        return SysUserConverterMapper.INSTANCE.convert(sysUserService.getByPrimaryKey(sysUser.getId()));
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
        final SysUser updateUser = SysUserConverterMapper.INSTANCE.updateConvert(request);
        updateUser.setVersion(sysUser.getVersion());
        sysUserService.update(updateUser);

        // 处理用户关联角色
        relativeUserRole(sysUser.getUserId(), request.getRoleIdList());
        return SysUserConverterMapper.INSTANCE.convert(sysUserService.getByPrimaryKey(request.getId()));
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
     * 系统用户登录
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse loginByPassword(LoginRequest request) {
        // 验证码白名单
        final List<String> whiteLoginNameList = authenticationProperties.getBiz().getWhiteLoginNameList();
        if (CollectionUtil.isEmpty(whiteLoginNameList) || !whiteLoginNameList.contains(request.getLoginName())) {
            // 获取随机数对应的验证码
            final String verifyCode = stringRedisTemplate.opsForValue()
                    .get(CacheKeys.getCaptchaKey(request.getTokenId()));
            PreconditionUtil.checkArgument(Objects.nonNull(verifyCode), BizCode.VERIFY_CODE_EXPIRED);
            PreconditionUtil.checkArgument(
                    Objects.equals(verifyCode, request.getVerifyCode()), BizCode.VERIFY_CODE_NOT_MAPPING);
        }

        final String loginName = request.getLoginName();
        SysUser sysUser = sysUserService.getByLoginName(loginName);
        // 这里要根据实际情况提示信息， 如果是C端应用，最好模糊提示，不要明确说明是用户不存在
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.LOGIN_NAME_NOT_EXIST);

        PreconditionUtil.checkArgument(
                Objects.equals(SysUserStatusEnum.ACTIVE.getCode(), sysUser.getStatus()), BizCode.SYS_USER_DISABLE);

        final String password = request.getPassword();
        String secretPassword = SecureUtil.signWithHMac(password, sysUser.getUserId());
        sysUser = sysUserService.getByLoginNameAndPassword(loginName, secretPassword);
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.LOGIN_PASSWORD_ERROR);

        // 更新用户最后一次登录时间
        sysUser.setLastLoginTime(LocalDateTime.now());
        sysUserService.update(sysUser);
        // 有些时间格式的数据不重新查询出来是有误差的
        sysUser = sysUserService.getByUserId(sysUser.getUserId());

        long loginTimeMillis = DateUtils.toDefaultMills(sysUser.getLastLoginTime());
        UserClaim userClaim = new UserClaim();
        userClaim.setUserId(Convert.toStr(sysUser.getUserId()))
                .setUsername(sysUser.getLoginName())
                .setCredit(WebUtil.getHost())
                // 记录用户当前登录时间
                .setLastLoginTime(loginTimeMillis)
                .setLastModifyPasswordTime(DateUtils.toDefaultMills(sysUser.getLastPwdResetTime()));
        AuthenticateToken authenticateToken = TokenUtil.createToken(userClaim);

        // 处理登录事件
        final UserLoginHistoryDTO userLoginHistoryDTO = UserLoginHistoryDTO.builder()
                .userId(sysUser.getUserId())
                .loginName(sysUser.getLoginName())
                .token(authenticateToken.getToken())
                .loginTime(sysUser.getLastLoginTime())
                .loginIp(WebUtil.getHost())
                .loginAddress(WebUtil.getCurRequest()
                        .getLocale()
                        .getDisplayCountry())
                .build();
        applicationContext.publishEvent(new SysUserLoginEvent(this, userLoginHistoryDTO));

        final LoginResponse response = new LoginResponse();
        return response.setToken(authenticateToken.getToken());
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
                result, SysUserConverterMapper.INSTANCE::convert);
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
        final SysUserDetailResponse response = SysUserConverterMapper.INSTANCE.convertDetailResponse(sysUser);
        response.setRoleList(sysUserRoleService.getUserActiveRoleList(request.getUserId()));
        return response;
    }
}
