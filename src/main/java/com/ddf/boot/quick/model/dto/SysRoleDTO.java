package com.ddf.boot.quick.model.dto;

import java.util.Date;
import lombok.Data;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 15:02
 */
@Data
public class SysRoleDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 排序
     */
    private Integer sort;

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
    private Date createTime;

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
    private Date modifyTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 版本号
     */
    private Integer version;
}
