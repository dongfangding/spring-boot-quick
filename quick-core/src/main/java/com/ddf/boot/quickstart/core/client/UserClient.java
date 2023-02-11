package com.ddf.boot.quickstart.core.client;

import com.ddf.boot.common.authentication.model.UserClaim;
import com.ddf.boot.common.authentication.util.UserContextUtil;
import com.ddf.boot.quickstart.core.entity.SysUser;
import com.ddf.boot.quickstart.core.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>用户帮助类</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/27 23:17
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Slf4j
public class UserClient {

    private final ISysUserService sysUserService;

    /**
     * 获取当前用户id
     *
     * @return
     */
    public Long currentUserId() {
        return Long.parseLong(UserContextUtil.getUserId());
    }

    /**
     * 获取当前用户信息
     *
     * @return
     */
    public UserClaim currentUserClaim() {
        return UserContextUtil.getUserClaim();
    }

    /**
     * 当前用户信息对象
     *
     * @return
     */
    public SysUser currentUserInfo() {
        return sysUserService.getByUserId(UserContextUtil.getUserId());
    }
}
