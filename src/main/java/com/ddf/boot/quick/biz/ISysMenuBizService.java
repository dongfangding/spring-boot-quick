package com.ddf.boot.quick.biz;

import com.ddf.boot.quick.model.dto.SysMenuDTO;
import com.ddf.boot.quick.model.request.SysMenuCreateRequest;
import com.ddf.boot.quick.model.request.SysMenuUpdateRequest;

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
}
