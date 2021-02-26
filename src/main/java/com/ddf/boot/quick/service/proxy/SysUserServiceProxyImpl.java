package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.mapper.SysUserMapper;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.model.request.SysUserPageRequest;
import com.ddf.boot.quick.service.ISysUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>service实现的代理层， 这一层的目的是， 由于使用了mybatis-plus， 所以省略了dao层， 将service层完全只关注查询， 但是期望将来能够在查询上做缓存
 * 处理， 那么有些方法， 比如getById，这些方法本身service就存在， 想要在这个方法上加逻辑，就得重新再写一个方法然后调用父类的方法， 这样显的很不清晰， 所以
 * 直接抽出来一个代理层， 在代理层上做缓存或者别的事情， 但是这个代理层本身也会存在要注意的问题，由于真的使用了静态代理， 在注入的时候要小心同一个接口
 * 有两个实现的问题， 这里将代理层作为默认注入</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/23 11:02
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysUserServiceProxyImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysUserService sysUserServiceImpl;

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    @Override
    public SysUser getByPrimaryKey(Long id) {
        return sysUserServiceImpl.getByPrimaryKey(id);
    }

    /**
     * 根据userId获取记录
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser getByUserId(String userId) {
        return sysUserServiceImpl.getByUserId(userId);
    }

    /**
     * 根据userId集合获取用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public List<SysUser> getByUserIds(List<String> userIds) {
        return sysUserServiceImpl.getByUserIds(userIds);
    }

    /**
     * 新增记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public boolean insert(SysUser sysUser) {
        return sysUserServiceImpl.insert(sysUser);
    }

    /**
     * 更新记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public boolean update(SysUser sysUser) {
        return sysUserServiceImpl.update(sysUser);
    }

    /**
     * 根据登录账号和密码获取用户信息
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public SysUser getByLoginNameAndPassword(String loginName, String password) {
        return sysUserServiceImpl.getByLoginNameAndPassword(loginName, password);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName
     * @return
     */
    @Override
    public SysUser getByLoginName(String loginName) {
        return sysUserServiceImpl.getByLoginName(loginName);
    }

    /**
     * 根据手机号获取用户
     *
     * @param mobile
     * @return
     */
    @Override
    public SysUser getByMobile(String mobile) {
        return sysUserServiceImpl.getByMobile(mobile);
    }

    /**
     * 根据昵称获取用户
     *
     * @param nickname
     * @return
     */
    @Override
    public SysUser getByNickname(String nickname) {
        return sysUserServiceImpl.getByNickname(nickname);
    }

    /**
     * 系统用户分页查询
     *
     * @param request
     * @return
     */
    @Override
    public PageResult<SysUser> pageList(SysUserPageRequest request) {
        return sysUserServiceImpl.pageList(request);
    }
}
