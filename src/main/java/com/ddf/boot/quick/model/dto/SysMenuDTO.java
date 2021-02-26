package com.ddf.boot.quick.model.dto;

import com.ddf.boot.common.core.constant.IUserIdCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

/**
 * <p>系统菜单DTO</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/26 10:25
 */
@Data
public class SysMenuDTO implements IUserIdCollection, Serializable {

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
     * 创建人id
     */
    private String createBy;

    /**
     * 创建人名称
     */
    private String createByName;

    /**
     * 创建时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private String modifyBy;

    /**
     * 修改人名称
     */
    private String modifyByName;

    /**
     * 修改时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifyTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 版本号
     */
    private Integer version;

    @Override
    public Set<String> getUserIds() {
        return Sets.newHashSet(createBy, modifyBy);
    }
}
