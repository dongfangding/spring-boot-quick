package com.ddf.boot.quick.model.request;

import com.ddf.boot.common.core.model.PageRequest;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * <p>系统角色分页查询</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 15:11
 */
@Data
public class SysRolePageRequest  implements PageRequest {

    /**
     * 页数, 从1开始
     */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum;

    /**
     * 每页显示条数
     */
    @Min(value = 1, message = "每页显示条数不能小于1")
    private Integer pageSize;

    /**
     * 角色名称
     */
    private String roleName;
}
