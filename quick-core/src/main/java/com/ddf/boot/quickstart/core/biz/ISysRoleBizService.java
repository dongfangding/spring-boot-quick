package com.ddf.boot.quickstart.core.biz;



import com.ddf.boot.common.api.model.common.CommonSwitchRequest;
import com.ddf.boot.common.api.model.common.PageResult;
import com.ddf.boot.quickstart.api.dto.SysRoleDTO;
import com.ddf.boot.quickstart.api.request.sys.SysRoleCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysRolePageRequest;

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
    SysRoleDTO saveOrUpdate(SysRoleCreateRequest request);

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
