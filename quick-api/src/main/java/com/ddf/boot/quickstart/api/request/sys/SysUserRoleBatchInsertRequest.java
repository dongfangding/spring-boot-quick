package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 批量创建用户-角色关联请求对象
 *
 * @author dongfang.ding
 * @date 2021/2/10 0010 17:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRoleBatchInsertRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 用户id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 角色集合
     */
    @NotBlank(message = "角色集合不能为空")
    private Set<Long> roleIdList;

}
