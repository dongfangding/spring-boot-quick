package com.ddf.boot.quick.model.request;

import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * <p>角色授权请求体</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:55
 */
@Data
public class SysRoleMenuAuthorizationRequest {

    /**
     * 要授权的角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    /**
     * 授权菜单列表， 覆盖方式
     */
    private Set<Long> menuIds;
}
