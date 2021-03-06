package com.ddf.boot.quick.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ddf.boot.quick.model.entity.SysMenu;
import java.util.List;

/**
 * <p>
 * 菜单表 服务类, 由于plus功能的封装， 该service用来替代dao的作用，禁止在该类中也业务代码， 建议另外用bizService承载业务
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 根据主键获取记录
     *
     * @param id
     * @return
     */
    default SysMenu getByPrimaryKey(Long id) {
        return getById(id);
    }

    /**
     * 新增记录
     *
     * @param sysMenu
     * @return
     */
    boolean insert(SysMenu sysMenu);


    /**
     * 更新记录
     * @param sysMenu
     * @return
     */
    boolean update(SysMenu sysMenu);

    /**
     * 根据菜单名称查询记录
     *
     * @param menuName
     * @return
     */
    SysMenu getByMenuName(String menuName);

    /**
     * 查询全部菜单
     *
     * @return
     */
    List<SysMenu> listAll();

}
