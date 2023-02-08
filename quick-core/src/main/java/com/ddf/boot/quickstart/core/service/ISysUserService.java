package com.ddf.boot.quickstart.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.quickstart.api.request.sys.SysUserPageRequest;
import com.ddf.boot.quickstart.core.entity.SysUser;
import java.util.List;

/**
 * <p>
 * 系统用户表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 根据id获取记录
     *
     * @param id
     * @return
     */
    default SysUser getByPrimaryKey(Long id) {
        return getById(id);
    }

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    boolean deleteByPrimaryKey(Long id);

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
    boolean insert(SysUser sysUser);


    /**
     * 更新记录
     * @param sysUser
     * @return
     */
    boolean update(SysUser sysUser);

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

    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    PageResult<SysUser> pageList(SysUserPageRequest request);
}
