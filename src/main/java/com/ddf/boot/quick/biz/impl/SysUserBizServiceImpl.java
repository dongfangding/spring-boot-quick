package com.ddf.boot.quick.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.ddf.boot.common.core.config.GlobalProperties;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.model.UserClaim;
import com.ddf.boot.common.core.util.DateUtils;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.SecureUtil;
import com.ddf.boot.common.core.util.WebUtil;
import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import com.ddf.boot.common.jwt.util.JwtUtil;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.common.exception.BizCode;
import com.ddf.boot.quick.common.redis.CacheKeys;
import com.ddf.boot.quick.converter.mapper.SysUserConverterMapper;
import com.ddf.boot.quick.event.SysUserLoginEvent;
import com.ddf.boot.quick.helper.SysUserHelper;
import com.ddf.boot.quick.model.dto.SysUserDTO;
import com.ddf.boot.quick.model.dto.UserLoginHistoryDTO;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.BatchInsertSysUserRoleRequest;
import com.ddf.boot.quick.model.request.CreateSysUserRequest;
import com.ddf.boot.quick.model.request.LoginRequest;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.model.request.UpdateSysUserRequest;
import com.ddf.boot.quick.model.response.LoginResponse;
import com.ddf.boot.quick.service.ISysUserRoleService;
import com.ddf.boot.quick.service.ISysUserService;
import groovy.util.logging.Slf4j;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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

    private final SnowflakeServiceHelper snowflakeServiceHelper;

    private final ISysUserRoleService sysUserRoleService;

    private final StringRedisTemplate stringRedisTemplate;

    private final GlobalProperties globalProperties;

    private final ApplicationContext applicationContext;

    private final SysUserHelper sysUserHelper;

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserDTO create(CreateSysUserRequest request) {
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

        String userId = snowflakeServiceHelper.getStringId();
        SysUser sysUser = SysUserConverterMapper.INSTANCE.requestConvert(request);
        sysUser.setUserId(userId);
        sysUser.setPassword(SecureUtil.signWithHMac(request.getPassword(), userId));

        // 处理用户关联角色
        Set<String> roleIdList = request.getRoleIdList();
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            BatchInsertSysUserRoleRequest batchInsertSysUserRoleRequest = BatchInsertSysUserRoleRequest.builder()
                    .userId(userId)
                    .roleIdList(roleIdList)
                    .build();
            sysUserRoleService.batchRelativeUser(batchInsertSysUserRoleRequest);
        }
        sysUserService.save(sysUser);
        return SysUserConverterMapper.INSTANCE.convert(sysUser);
    }

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @Override
    public SysUserDTO update(UpdateSysUserRequest request) {
        return null;
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
        final List<String> blankLoginNameList = globalProperties.getBlankLoginNameList();
        if (CollectionUtil.isNotEmpty(blankLoginNameList) && !blankLoginNameList.contains(request.getLoginName())) {
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

        final String password = request.getPassword();
        String secretPassword = SecureUtil.signWithHMac(password, sysUser.getUserId());
        sysUser = sysUserService.getByLoginNameAndPassword(loginName, secretPassword);
        PreconditionUtil.checkArgument(Objects.nonNull(sysUser), BizCode.LOGIN_PASSWORD_ERROR);

        final LocalDateTime loginTime = LocalDateTime.now();
        long loginTimeMillis = DateUtils.toDefaultMills(loginTime);

        UserClaim userClaim = new UserClaim();
        userClaim.setUserId(Convert.toStr(sysUser.getId()))
                .setUsername(sysUser.getLoginName())
                .setCredit(WebUtil.getHost())
                // 记录用户当前登录时间
                .setLastLoginTime(loginTimeMillis);
        if (Objects.nonNull(sysUser.getLastPwdResetTime())) {
            userClaim.setLastModifyPasswordTime(loginTimeMillis);
        }
        String jwtToken = JwtUtil.defaultJws(userClaim);
        // 更新用户最后一次登录时间
        sysUser.setLastLoginTime(loginTime);
        sysUserService.update(sysUser);

        // 处理登录事件
        final UserLoginHistoryDTO userLoginHistoryDTO = UserLoginHistoryDTO.builder()
                .userId(sysUser.getUserId())
                .loginName(sysUser.getLoginName())
                .token(jwtToken)
                .loginTime(loginTime)
                .loginIp(WebUtil.getHost())
                .loginAddress(WebUtil.getCurRequest()
                        .getLocale()
                        .getDisplayCountry())
                .build();
        applicationContext.publishEvent(new SysUserLoginEvent(this, userLoginHistoryDTO));

        final LoginResponse response = new LoginResponse();
        return response.setToken(jwtToken);
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
            return PageUtil.empty();
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
}
