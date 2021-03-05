package com.ddf.boot.quick.biz;

import com.ddf.boot.quick.model.request.SysConfigCreateRequest;
import com.ddf.boot.quick.model.request.SysConfigUpdateRequest;
import com.ddf.boot.quick.model.response.SysConfigConsoleResponse;

/**
 * <p>配置业务类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/03/05 22:41
 */
public interface ISysConfigBizService {

    /**
     * 新增配置
     *
     * @param request
     * @return
     */
    SysConfigConsoleResponse create(SysConfigCreateRequest request);

    /**
     * 修改配置
     *
     * @param request
     * @return
     */
    SysConfigConsoleResponse update(SysConfigUpdateRequest request);
}
