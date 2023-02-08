package com.ddf.boot.quickstart.core.biz.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ddf.boot.quick.biz.ISysRoleMenuBizService;
import com.ddf.boot.quick.helper.SysUserHelper;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.model.request.SysRoleMenuBuildRoleMenuRequest;
import com.ddf.boot.quick.model.response.SysMenuTreeResponse;
import com.ddf.boot.quick.service.ISysMenuService;
import com.ddf.boot.quick.service.ISysRoleMenuService;
import com.ddf.boot.quick.util.TreeUtil;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>角色菜单关联业务处理类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:27
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class SysRoleMenuBizServiceImpl implements ISysRoleMenuBizService {

    private final ISysRoleMenuService sysRoleMenuService;

    private final ISysMenuService sysMenuService;

    private final SysUserHelper sysUserHelper;

    /**
     * 构建用户的左侧菜单树，即用户有哪些菜单权限
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuTreeResponse> buildUserMenuTree(String userId) {
        if (sysUserHelper.isAdmin(userId)) {
            final List<SysMenu> all = sysMenuService.listAll();
            if (CollectionUtil.isEmpty(all)) {
                return Collections.emptyList();
            }
            return TreeUtil.convertMenuTree(all);
        }
        return TreeUtil.convertMenuTree(sysRoleMenuService.getUserActiveMenu(userId));
    }

    /**
     * 构建角色授权树， 加载全部菜单树， 已授权的会用属性标识已被授权
     *
     * @param request
     * @return
     */
    @Override
    public List<SysMenuTreeResponse> buildRoleMenuTree(SysRoleMenuBuildRoleMenuRequest request) {
        // 获取当前角色下的菜单列表
        final List<Long> menuIds = sysRoleMenuService.getRoleActiveMenuIds(request.getRoleId());
        return TreeUtil.convertMenuTree(sysMenuService.listAll(), menuIds);
    }

    /**
     * 角色授权
     *
     * @param request
     * @return 返回授权菜单数量
     */
    @Override
    public Integer authorization(SysRoleMenuAuthorizationRequest request) {
        // 先删除角色已有权限
        sysRoleMenuService.deleteByRoleId(request.getRoleId());
        if (CollectionUtil.isNotEmpty(request.getMenuIds())) {
            return sysRoleMenuService.batchInsert(request);
        }
        return 0;
    }
}
