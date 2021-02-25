package com.ddf.boot.quick.model.dto;

import lombok.Data;

/**
 * <p>系统用户角色</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/23 22:11
 */
@Data
public class SysUserRoleDTO {

    /**
     * 用户Id
     *
     */
    private String userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 是否超管，超管拥有全部权限，但不能通过系统创建 0 否 1是
     *
     */
    private Integer isAdmin;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否激活 0 否 1 是
     */
    private Integer isActive;
}
