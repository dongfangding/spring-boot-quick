package com.ddf.boot.quickstart.core.biz;


import com.ddf.boot.quickstart.api.request.sys.SysConfigCreateRequest;
import com.ddf.boot.quickstart.api.request.sys.SysConfigUpdateRequest;
import com.ddf.boot.quickstart.api.response.sys.SysConfigConsoleResponse;

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
