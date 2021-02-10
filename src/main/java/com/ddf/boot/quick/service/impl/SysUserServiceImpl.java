package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.dao.SysUserDao;
import com.ddf.boot.quick.mapper.SysUserMapper;
import com.ddf.boot.quick.model.entity.SysUser;
import com.ddf.boot.quick.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserDao sysUserDao;

    /**
     * 新增记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public int insert(SysUser sysUser) {
        return sysUserDao.insert(sysUser);
    }

    /**
     * 更新记录
     *
     * @param sysUser
     * @return
     */
    @Override
    public int update(SysUser sysUser) {
        return sysUserDao.update(sysUser);
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
        return sysUserDao.getByLoginNameAndPassword(loginName, password);
    }
}
