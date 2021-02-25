package com.ddf.boot.quick.biz;

import com.ddf.boot.common.core.model.CommonSwitchRequest;
import com.ddf.boot.common.core.model.PageResult;
import com.ddf.boot.quick.model.dto.SysRoleDTO;
import com.ddf.boot.quick.model.request.CreateSysRoleRequest;
import com.ddf.boot.quick.model.request.SysRolePageRequest;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 14:59
 */
public interface ISysRoleBizService {

    /**
     * 创建系统角色
     *
     * @param request
     * @return
     */
    SysRoleDTO saveOrUpdate(CreateSysRoleRequest request);

    /**
     * 系统角色分页查询
     *
     * @param request
     * @return
     */
    PageResult<SysRoleDTO> pageList(SysRolePageRequest request);

    /**
     * 启用禁用状态切换开关
     *
     * @param request
     * @return
     */
    Boolean activeSwitch(CommonSwitchRequest request);
}
