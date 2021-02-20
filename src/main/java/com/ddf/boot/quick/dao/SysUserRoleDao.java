package com.ddf.boot.quick.dao;

import com.ddf.boot.quick.mapper.SysUserRoleMapper;
import com.ddf.boot.quick.model.entity.SysUserRole;
import com.ddf.boot.quick.model.request.BatchInsertSysUserRoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 用户-角色关联DAO
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 17:57
 */
@Repository
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserRoleDao {

    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 新增记录
     *
     * @param sysUserRole
     * @return
     */
    public int insert(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insert(sysUserRole);
    }


    /**
     * 更新记录
     * @param sysUserRole
     * @return
     */
    public int update(SysUserRole sysUserRole) {
        return sysUserRoleMapper.updateById(sysUserRole);
    }


    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    public int batchRelativeUser(BatchInsertSysUserRoleRequest request) {
        return sysUserRoleMapper.batchRelativeUser(request);
    }

}
