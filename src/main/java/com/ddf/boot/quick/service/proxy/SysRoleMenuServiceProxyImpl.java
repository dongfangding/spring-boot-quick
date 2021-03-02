package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysRoleMenuMapper;
import com.ddf.boot.quick.model.entity.SysRoleMenu;
import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.service.ISysRoleMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>角色菜单关联查询层代理类， 用来加缓存层</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:28
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysRoleMenuServiceProxyImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

    private final ISysRoleMenuService sysRoleMenuServiceImpl;

    /**
     * 根据主键删除记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteByRoleId(Long id) {
        return sysRoleMenuServiceImpl.deleteByRoleId(id);
    }

    /**
     * 新增记录
     *
     * @param sysRoleMenu
     * @return
     */
    @Override
    public boolean insert(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuServiceImpl.insert(sysRoleMenu);
    }

    /**
     * 更新记录
     *
     * @param sysRoleMenu
     * @return
     */
    @Override
    public boolean update(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuServiceImpl.update(sysRoleMenu);
    }

    /**
     * 获取用户已分配的菜单id集合
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> getUserActiveMenuIds(String userId) {
        return sysRoleMenuServiceImpl.getUserActiveMenuIds(userId);
    }

    /**
     * 查询某个角色下已授权菜单id集合
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Long> getRoleActiveMenuIds(Long roleId) {
        return sysRoleMenuServiceImpl.getRoleActiveMenuIds(roleId);
    }

    /**
     * 批量给角色关联菜单
     *
     * @param request
     * @return
     */
    @Override
    public Integer batchInsert(SysRoleMenuAuthorizationRequest request) {
        return sysRoleMenuServiceImpl.batchInsert(request);
    }
}
