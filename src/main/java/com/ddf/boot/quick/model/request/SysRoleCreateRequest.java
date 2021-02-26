package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.validator.constraint.LogicValueValidator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * <p>创建系统角色请求类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 15:00
 */
@Data
public class SysRoleCreateRequest {

    /**
     * id 当为空时执行新增， 当不为空时执行编辑
     */
    private Long id;

    /**
     * 角色名称
     */
    @NotEmpty(message = "角色名称不能为空")
    @Size(min = 1, max = 32, message = "角色名称长度必须位于1到32个字符之间")
    private String roleName;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /**
     *
     * 是否激活 0 否 1 是
     */
    @LogicValueValidator(values = {0, 1}, message = "是否激活有效值错误")
    private Integer isActive;

}
