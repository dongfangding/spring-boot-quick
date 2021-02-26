package com.ddf.boot.quick.service.proxy;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.quick.mapper.SysMenuMapper;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/26 10:02
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
@Primary
public class SysMenuServiceProxyImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final ISysMenuService sysMenuServiceImpl;

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    @Override
    public SysMenu getByPrimaryKey(Long id) {
        return sysMenuServiceImpl.getByPrimaryKey(id);
    }


    /**
     * 新增记录
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean insert(SysMenu sysMenu) {
        return sysMenuServiceImpl.insert(sysMenu);
    }

    /**
     * 更新记录
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean update(SysMenu sysMenu) {
        return sysMenuServiceImpl.update(sysMenu);
    }

    /**
     * 根据菜单名称查询记录
     *
     * @param menuName
     * @return
     */
    @Override
    public SysMenu getByMenuName(String menuName) {
        return sysMenuServiceImpl.getByMenuName(menuName);
    }
}
