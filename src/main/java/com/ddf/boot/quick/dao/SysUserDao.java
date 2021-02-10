package com.ddf.boot.quick.dao;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quick.mapper.SysUserMapper;
import com.ddf.boot.quick.model.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统用户DAO
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 11:40
 */
@Repository
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysUserDao {

    private final SysUserMapper sysUserMapper;

    /**
     * 新增记录
     *
     * @param sysUser
     * @return
     */
    public int insert(SysUser sysUser) {
        return sysUserMapper.insert(sysUser);
    }


    /**
     * 更新记录
     * @param sysUser
     * @return
     */
    public int update(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser);
    }

    /**
     * 根据登录账号和密码获取用户信息
     *
     * @param loginName
     * @param password
     * @return
     */
    public SysUser getByLoginNameAndPassword(String loginName, String password) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getLoginName, loginName)
                .eq(SysUser::getPassword, password);
        return sysUserMapper.selectOne(wrapper);
    }
}
