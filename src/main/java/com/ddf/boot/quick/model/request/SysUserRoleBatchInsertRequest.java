package com.ddf.boot.quick.model.request;

import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;


/**
 * 批量创建用户-角色关联请求对象
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 17:58
 */
@Data
@Builder
public class SysUserRoleBatchInsertRequest {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    /**
     * 角色集合
     */
    @NotEmpty(message = "角色集合不能为空")
    private Set<Long> roleIdList;

}
