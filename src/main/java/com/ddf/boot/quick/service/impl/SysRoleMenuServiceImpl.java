package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.model.BaseDomain;
import com.ddf.boot.quick.mapper.SysRoleMenuMapper;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.model.entity.SysRoleMenu;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.service.ISysRoleMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色与菜单关联表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    private final SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 根据主键删除记录
     *
     * @param roleId
     * @return
     */
    @Override
    public boolean deleteByRoleId(Long roleId) {
        final LambdaUpdateWrapper<SysRoleMenu> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysRoleMenu::getRoleId, roleId)
                .eq(SysRoleMenu::getIsDel, BaseDomain.IS_DEL_LOGIC_VALID_VALUE);
        return super.remove(wrapper);
    }

    /**
     * 新增记录
     *
     * @param sysRoleMenu
     * @return
     */
    @Override
    public boolean insert(SysRoleMenu sysRoleMenu) {
        return super.save(sysRoleMenu);
    }

    /**
     * 更新记录
     *
     * @param sysRoleMenu
     * @return
     */
    @Override
    public boolean update(SysRoleMenu sysRoleMenu) {
        return super.updateById(sysRoleMenu);
    }

    /**
     * 获取用户已分配的菜单
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getUserActiveMenu(String userId) {
        return sysRoleMenuMapper.getUserActiveMenu(userId);
    }

    /**
     * 查询某个角色下已授权菜单id集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getRoleActiveMenuIds(Long roleId) {
        return sysRoleMenuMapper.getRoleActiveMenuIds(roleId);
    }

    /**
     * 批量给角色关联菜单
     *
     * @param request
     * @return
     */
    @Override
    public Integer batchInsert(SysRoleMenuAuthorizationRequest request) {
        return sysRoleMenuMapper.batchInsert(request);
    }
}
