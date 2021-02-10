package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.dao.SysUserRoleDao;
import com.ddf.boot.quick.mapper.SysUserRoleMapper;
import com.ddf.boot.quick.model.bo.BatchInsertSysUserRoleRequest;
import com.ddf.boot.quick.model.entity.SysUserRole;
import com.ddf.boot.quick.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户-角色关联表 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    private final SysUserRoleDao sysUserRoleDao;

    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    @Override
    public int batchRelativeUser(BatchInsertSysUserRoleRequest request) {
        return sysUserRoleDao.batchRelativeUser(request);
    }
}
