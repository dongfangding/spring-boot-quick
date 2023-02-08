package com.ddf.boot.quickstart.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ddf.boot.common.core.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu" )
public class SysMenu extends BaseDomain {

    private static final long serialVersionUID = 1L;

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
     * 0 目录 1 菜单 2 按钮
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


}
