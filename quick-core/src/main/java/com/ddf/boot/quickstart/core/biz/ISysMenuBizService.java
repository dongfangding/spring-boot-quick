package com.ddf.boot.quickstart.core.biz;


import com.ddf.boot.quickstart.api.dto.SysMenuDTO;
import com.ddf.boot.quickstart.api.request.sys.SysMenuCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysMenuUpdateRequest;
import com.ddf.boot.quickstart.api.response.sys.SysMenuTreeResponse;
import java.util.List;

/**
 * <p>系统菜单业务类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/26 10:08
 */
public interface ISysMenuBizService {

    /**
     * 创建系统菜单
     *
     * @param request
     * @return
     */
    SysMenuDTO create(SysMenuCreateRequest request);

    /**
     * 更新菜单
     *
     * @param request
     * @return
     */
    SysMenuDTO update(SysMenuUpdateRequest request);

    /**
     * 构建菜单树
     *
     * @return
     */
    List<SysMenuTreeResponse> buildMenuTree();
}
