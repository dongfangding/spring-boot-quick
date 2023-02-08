package com.ddf.boot.quickstart.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quickstart.api.dto.SysUserRoleDTO;
import com.ddf.boot.quickstart.api.request.sys.SysUserRoleBatchInsertRequest;
import com.ddf.boot.quickstart.core.entity.SysUserRole;
import java.util.List;

/**
 * <p>
 * 用户-角色关联表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysUserRoleService extends IService<SysUserRole> {


    /**
     * 新增记录
     *
     * @param sysUserRole
     * @return
     */
    boolean insert(SysUserRole sysUserRole);


    /**
     * 更新记录
     * @param sysUserRole
     * @return
     */
    boolean update(SysUserRole sysUserRole);

    /**
     * 删除用户的角色列表
     *
     * @param userId
     * @return
     */
    int deleteUserRole(String userId);

    /**
     * 批量关联用户角色
     *
     * @param request
     * @return
     */
    int batchRelativeUser(SysUserRoleBatchInsertRequest request);

    /**
     * 获取用户有效的角色列表
     *
     * @param userId
     * @return
     */
    List<SysUserRoleDTO> getUserActiveRoleList(String userId);
}
