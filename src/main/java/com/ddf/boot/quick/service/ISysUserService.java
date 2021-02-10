package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysUser;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 新增记录
     *
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);


    /**
     * 更新记录
     * @param sysUser
     * @return
     */
    int update(SysUser sysUser);

    /**
     * 根据登录账号和密码获取用户信息
     *
     * @param loginName
     * @param password
     * @return
     */
    SysUser getByLoginNameAndPassword(String loginName, String password);

}
