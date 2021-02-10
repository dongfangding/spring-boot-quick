package com.ddf.boot.quick.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.common.core.util.SecureUtil;
import com.ddf.boot.common.ids.helper.SnowflakeServiceHelper;
import com.ddf.boot.quick.biz.ISysUserBizService;
import com.ddf.boot.quick.common.exception.BizCode;
import com.ddf.boot.quick.converter.mapper.SysUserConverterMapper;
import com.ddf.boot.quick.model.bo.BatchInsertSysUserRoleRequest;
import com.ddf.boot.quick.model.bo.CreateSysUserRequest;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.vo.CreateSysUserResponse;
import com.ddf.boot.quick.service.ISysUserRoleService;
import com.ddf.boot.quick.service.ISysUserService;
import groovy.util.logging.Slf4j;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserBizServiceImpl implements ISysUserBizService {

    private final ISysUserService sysUserService;

    private final SnowflakeServiceHelper snowflakeServiceHelper;

    private final ISysUserRoleService sysUserRoleService;

    /**
     * 创建系统用户
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreateSysUserResponse create(CreateSysUserRequest request) {
        // 重复性判断
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByLoginName(request.getLoginName())),
                BizCode.LOGIN_NAME_REPEAT);
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByNickname(request.getNickname())),
                BizCode.NICK_NAME_REPEAT);
        PreconditionUtil.checkArgument(Objects.isNull(sysUserService.getByMobile(request.getMobile())),
                BizCode.MOBILE_REPEAT);

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
}
