package com.ddf.boot.quick.biz;

import com.ddf.boot.quick.model.request.SysRoleMenuAuthorizationRequest;
import com.ddf.boot.quick.model.request.SysRoleMenuBuildRoleMenuRequest;
import com.ddf.boot.quick.model.response.SysMenuTreeResponse;
import java.util.List;

/**
 * <p>角色菜单关联业务处理类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/02 13:12
 */
public interface ISysRoleMenuBizService {

    /**
     * 构建角色授权树， 加载全部菜单树， 已授权的会用属性标识已被授权
     *
     * @param request
     * @return
     */
    List<SysMenuTreeResponse> buildRoleMenuTree(SysRoleMenuBuildRoleMenuRequest request);

    /**
     * 角色授权
     *
     * @param request
     * @return 返回授权菜单数量
     */
    Integer authorization(SysRoleMenuAuthorizationRequest request);
}
