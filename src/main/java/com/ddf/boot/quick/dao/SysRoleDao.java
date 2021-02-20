package com.ddf.boot.quick.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.quick.mapper.SysRoleMapper;
import com.ddf.boot.quick.model.entity.SysRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 系统角色
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 11:40
 */
@Repository
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysRoleDao {

    private final SysRoleMapper sysRoleMapper;


    /**
     * 新增记录
     *
     * @param sysRole
     * @return
     */
    public int insert(SysRole sysRole) {
        return sysRoleMapper.insert(sysRole);
    }


    /**
     * 更新记录
     * @param sysRole
     * @return
     */
    public int update(SysRole sysRole) {
        return sysRoleMapper.updateById(sysRole);
    }

    /**
     * 根据角色名字查询记录， 包含未激活的
     *
     * @param roleName
     * @return
     */
    public SysRole getByName(String roleName) {
        final LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysRole::getRoleName, roleName)
            .eq(SysRole::getIsDel, CommonLogic.TRUE.getLogic());
        return sysRoleMapper.selectOne(wrapper);
    }
}
