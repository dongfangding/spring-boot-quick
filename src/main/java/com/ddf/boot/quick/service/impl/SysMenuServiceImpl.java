package com.ddf.boot.quick.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ddf.boot.common.core.enumration.CommonLogic;
import com.ddf.boot.quick.mapper.SysMenuMapper;
import com.ddf.boot.quick.model.entity.SysMenu;
import com.ddf.boot.quick.service.ISysMenuService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    /**
     * 新增记录
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean insert(SysMenu sysMenu) {
        return super.save(sysMenu);
    }

    /**
     * 更新记录
     *
     * @param sysMenu
     * @return
     */
    @Override
    public boolean update(SysMenu sysMenu) {
        return super.updateById(sysMenu);
    }

    /**
     * 根据菜单名称查询记录
     *
     * @param menuName
     * @return
     */
    @Override
    public SysMenu getByMenuName(String menuName) {
        final LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenu::getMenuName, menuName)
                .eq(SysMenu::getIsDel, CommonLogic.FALSE.getLogic());
        return getOne(wrapper);
    }

    /**
     * 查询全部菜单
     *
     * @return
     */
    @Override
    public List<SysMenu> listAll() {
        final LambdaQueryWrapper<SysMenu> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysMenu::getIsDel, CommonLogic.FALSE.getLogic());
        wrapper.orderByAsc(SysMenu::getSort);
        return list(wrapper);
    }
}
