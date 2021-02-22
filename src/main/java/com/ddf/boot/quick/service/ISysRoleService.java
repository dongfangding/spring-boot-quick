package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.model.entity.SysRole;
import com.ddf.boot.quick.model.request.SysRolePageRequest;

/**
 * <p>
 * 角色表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
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
    boolean insert(SysRole sysRole);


    /**
     * 更新记录
     * @param sysRole
     * @return
     */
    boolean update(SysRole sysRole);

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    SysRole getByName(String roleName);

    /**
     * 角色分页查询
     *
     * @param request
     * @return
     */
    PageResult<SysRole> pageList(SysRolePageRequest request);
}
