package com.ddf.boot.quick.model.request;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * <p>创建系统角色请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 15:00
 */
@Data
public class CreateSysRoleRequest {

    /**
     * id 当为空时执行新增， 当不为空时执行编辑
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**
     * 排序
     */
    private Integer sort;

}
