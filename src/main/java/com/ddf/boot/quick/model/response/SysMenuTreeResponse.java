package com.ddf.boot.quick.model.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * <p>菜单树</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/01 22:24
 */
@Data
public class SysMenuTreeResponse {

    /**
     * id
     */
    private Long id;

    /**
     * 父级菜单id, 0为一级菜单
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 路由地址
     */
    private String routeUrl;

    /**
     * 0 菜单 1 按钮
     */
    private Integer menuType;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限标识，设计之初是使用目标方法的path进行映射， 如一个菜单下有多个接口地址，可以使用逗号分隔，如果分属一个控制器下，可以使用通配符；如/menu/**,
     */
    private String permission;

    /**
     * 是否激活 0 否 1 是
     */
    private Integer isActive;

    /**
     * 版本号
     */
    private Integer version;

    //------------------------------------------------树结构树形--------------------

    /**
     * 子节点
     */
    private List<SysMenuTreeResponse> children = new ArrayList<>();

    /**
     * 是否已授权
     *
     */
    private boolean selected;

}
