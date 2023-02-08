package com.ddf.boot.quickstart.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ddf.boot.common.core.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author mybatis-plus-generator
 * @since 2021-02-10
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role" )
public class SysRole extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否超管，超管拥有全部权限，但不能通过系统创建 0 否 1是
     *
     */
    private Integer isAdmin;

    /**
     * 是否激活 0 否 1 是
     */
    private Integer isActive;

}
