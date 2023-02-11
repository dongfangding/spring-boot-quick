package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>角色授权请求体</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenuAuthorizationRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

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
