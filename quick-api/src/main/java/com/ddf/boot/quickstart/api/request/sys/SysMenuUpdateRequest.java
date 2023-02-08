package com.ddf.boot.quickstart.api.request.sys;

import com.ddf.boot.common.api.validator.constraint.LogicValueValidator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* <p>创建系统菜单请求参数类</p >
*
* @author Snowball
* @version 1.0
* @date 2021/02/26 10:12
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuUpdateRequest {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 父级菜单id, 0为目录
     */
    @NotNull(message = "父类id不能为空")
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
     * 图标
     */
    private String icon;

    /**
     * 权限标识，设计之初是使用目标方法的path进行映射， 如一个菜单下有多个接口地址，可以使用逗号分隔，如果分属一个控制器下，可以使用通配符；如/menu/**,
     */
    private String permission;

    /**
     * 是否激活 0 否  1 是
     *
     */
    @LogicValueValidator
    private Integer isActive;

}
