package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysUserRole;
import com.ddf.boot.quick.model.request.BatchInsertSysUserRoleRequest;

/**
 * <p>
 * 用户-角色关联表 服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    int batchRelativeUser(BatchInsertSysUserRoleRequest request);
}
