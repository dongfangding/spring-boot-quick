package com.ddf.boot.quickstart.api.request.sys;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>构建角色菜单树请求</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:35
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleMenuBuildRoleMenuRequest implements Serializable {

    private static final long serialVersionUID = -8846320147721003035L;

    /**
     * 角色id
     */
    private Long roleId;
}
