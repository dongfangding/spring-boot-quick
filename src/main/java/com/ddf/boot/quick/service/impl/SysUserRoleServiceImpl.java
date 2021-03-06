package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysUserRoleMapper;
import com.ddf.boot.quick.model.dto.SysUserRoleDTO;
import com.ddf.boot.quick.model.entity.SysUserRole;
import com.ddf.boot.quick.model.request.SysUserRoleBatchInsertRequest;
import com.ddf.boot.quick.service.ISysUserRoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 新增记录
     *
     * @param sysUserRole
     * @return
     */
    @Override
    public boolean insert(SysUserRole sysUserRole) {
        return super.save(sysUserRole);
    }

    /**
     * 更新记录
     *
     * @param sysUserRole
     * @return
     */
    @Override
    public boolean update(SysUserRole sysUserRole) {
        return super.updateById(sysUserRole);
    }

    /**
     * 删除用户的角色列表
     *
     * @param userId
     * @return 删除条数
     */
    @Override
    public int deleteUserRole(String userId) {
        final LambdaQueryWrapper<SysUserRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUserRole::getUserId, userId);
        return baseMapper.delete(wrapper);
    }

    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    @Override
    public int batchRelativeUser(SysUserRoleBatchInsertRequest request) {
        return sysUserRoleMapper.batchRelativeUser(request);
    }

    /**
     * 获取用户有效的角色列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysUserRoleDTO> getUserActiveRoleList(String userId) {
        return sysUserRoleMapper.getUserActiveRoleList(userId);
    }
}
