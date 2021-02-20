package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysUser;
import java.util.List;

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
     * 根据userId获取记录
     *
     * @param userId
     * @return
     */
    SysUser getByUserId(String userId);


    /**
     * 根据userId集合获取用户信息
     *
     * @param userIds
     * @return
     */
    List<SysUser> getByUserIds(List<String> userIds);

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


    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    SysUser getByLoginName(String loginName);

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    SysUser getByMobile(String mobile);


    /**
     * 根据昵称获取用户
     *
     * @param nickname
     * @return
     */
    SysUser getByNickname(String nickname);

}
