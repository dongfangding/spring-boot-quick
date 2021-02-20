package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.dao.SysRoleDao;
import com.ddf.boot.quick.mapper.SysRoleMapper;
import com.ddf.boot.quick.model.entity.SysRole;
import com.ddf.boot.quick.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleDao sysRoleDao;

    /**
     * 新增记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public int insert(SysRole sysRole) {
        return sysRoleDao.insert(sysRole);
    }

    /**
     * 更新记录
     *
     * @param sysRole
     * @return
     */
    @Override
    public int update(SysRole sysRole) {
        return sysRoleDao.update(sysRole);
    }

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    @Override
    public SysRole getByName(String roleName) {
        return sysRoleDao.getByName(roleName);
    }
}
