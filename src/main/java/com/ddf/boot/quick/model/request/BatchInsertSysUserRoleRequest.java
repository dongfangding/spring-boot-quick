package com.ddf.boot.quick.model.request;

import java.util.Set;
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
public class BatchInsertSysUserRoleRequest {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色集合
     */
    private Set<Long> roleIdList;

}
