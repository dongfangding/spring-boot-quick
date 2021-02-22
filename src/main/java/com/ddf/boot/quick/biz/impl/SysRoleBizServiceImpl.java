package com.ddf.boot.quick.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.common.core.util.PageUtil;
import com.ddf.boot.common.core.util.PreconditionUtil;
import com.ddf.boot.quick.biz.ISysRoleBizService;
import com.ddf.boot.quick.common.exception.BizCode;
import com.ddf.boot.quick.converter.mapper.SysRoleConvertMapper;
import com.ddf.boot.quick.helper.SysUserHelper;
import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.entity.SysRole;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.CreateSysRoleRequest;
import com.ddf.boot.quick.model.request.SysRolePageRequest;
import com.ddf.boot.quick.service.ISysRoleService;
import com.ddf.boot.quick.service.ISysUserService;
import groovy.util.logging.Slf4j;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 15:14
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysRoleBizServiceImpl implements ISysRoleBizService {

    private final ISysRoleService sysRoleService;

    private final ISysUserService sysUserService;

    private final SysUserHelper sysUserHelper;

    /**
     * 创建系统角色
     *
     * @param request
     * @return
     */
    @Override
    public SysRoleDTO saveOrUpdate(CreateSysRoleRequest request) {
        final String roleName = request.getRoleName();
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        sysRole.setSort(request.getSort());

        final SysRole existSysRole = sysRoleService.getByName(roleName);
        if (Objects.isNull(request.getId())) {
            PreconditionUtil.checkArgument(Objects.isNull(existSysRole), BizCode.ROLE_NAME_EXIST);
            sysRoleService.insert(sysRole);
        } else {
            final SysRole oldSysRole = sysRoleService.getById(request.getId());
            PreconditionUtil.checkArgument(Objects.nonNull(oldSysRole), BizCode.ROLE_RECORD_NOT_EXIST);
            if (Objects.nonNull(existSysRole)) {
                PreconditionUtil.checkArgument(
                        Objects.equals(existSysRole.getId(), request.getId()), BizCode.ROLE_NAME_EXIST);
            }
            sysRole.setId(request.getId());
            sysRole.setVersion(oldSysRole.getVersion());
            sysRoleService.update(sysRole);
        }
        Set<String> operatorIdSet = new HashSet<>();
        if (Objects.nonNull(sysRole.getCreateBy())) {
            operatorIdSet.add(sysRole.getCreateBy());
        }
        if (Objects.nonNull(sysRole.getModifyBy())) {
            operatorIdSet.add(sysRole.getCreateBy());
        }
        final SysRoleDTO response = SysRoleConvertMapper.INSTANCE.convert(sysRole);
        if (CollectionUtil.isNotEmpty(operatorIdSet)) {
            final List<SysUser> sysUserList = sysUserService.getByUserIds(new ArrayList<>(operatorIdSet));
            final Map<String, SysUser> collect = sysUserList.stream()
                    .collect(Collectors.toMap(SysUser::getUserId, val -> val));
            if (CollectionUtil.isNotEmpty(collect)) {
                if (collect.containsKey(response.getCreateBy())) {
                    response.setCreateByName(collect.get(response.getCreateBy())
                            .getLoginName());
                }
                if (collect.containsKey(response.getModifyBy())) {
                    response.setModifyByName(collect.get(response.getModifyBy())
                            .getLoginName());
                }
            }
        }
        return response;
    }

    /**
     * 系统角色分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysRoleDTO> pageList(SysRolePageRequest request) {
        final PageResult<SysRole> result = sysRoleService.pageList(request);
        if (result.isEmpty()) {
            return PageUtil.empty();
        }
        final PageResult<SysRoleDTO> responsePageResult = PageUtil.convertPageResult(
                result, SysRoleConvertMapper.INSTANCE::convert);
        final List<SysRoleDTO> content = responsePageResult.getContent();
        final Map<String, SysUser> sysUserMap = sysUserHelper.getUserMap(content);

        SysUser sysUser;
        for (SysRoleDTO dto : content) {
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
