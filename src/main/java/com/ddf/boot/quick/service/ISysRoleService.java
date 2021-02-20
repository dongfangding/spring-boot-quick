package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysRole;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 新增记录
     *
     * @param sysRole
     * @return
     */
    int insert(SysRole sysRole);


    /**
     * 更新记录
     * @param sysRole
     * @return
     */
    int update(SysRole sysRole);

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    SysRole getByName(String roleName);
}
