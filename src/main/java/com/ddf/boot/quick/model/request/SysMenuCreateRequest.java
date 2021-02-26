package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.validator.constraint.LogicValueValidator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
* <p>创建系统菜单请求参数类</p >
*
* @author Snowball
* @version 1.0
* @date 2021/02/26 10:12
*/
@Data
public class SysMenuCreateRequest {

    /**
     * 父级菜单id, 0为一级菜单
     */
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    @Size(min = 1, max = 32, message = "菜单名称长度必须位于1到32个字符之间")
    private String menuName;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     * 路由地址
     */
    private String routeUrl;

    /**
     * 0 菜单 1 按钮
     */
    @NotNull(message = "菜单类型不能为空")
    @LogicValueValidator(values = {0, 1}, message = "菜单类型有效值错误")
    private Integer menuType;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限标识，设计之初是使用目标方法的path进行映射， 如一个菜单下有多个接口地址，可以使用逗号分隔，如果分属一个控制器下，可以使用通配符；如/menu/**,
     */
    private String permission;
}
