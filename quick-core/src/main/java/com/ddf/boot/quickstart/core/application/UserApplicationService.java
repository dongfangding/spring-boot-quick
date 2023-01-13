package com.ddf.boot.quickstart.core.application;

import com.ddf.boot.quickstart.api.request.auth.UserRegistryRequest;

/**
 * <p>用户应用层</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:39
 */
public interface UserApplicationService {

    /**
     * 注册账号
     *
     * @param request
     */
    void registry(UserRegistryRequest request);

}
