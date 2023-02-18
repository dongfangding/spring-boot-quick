package com.ddf.boot.quickstart.core.repository;

/**
 * <p>在线用户管理仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/06 20:18
 */
public interface OnlineUserRepository {

    /**
     * 设置在线用户
     *
     * @param userId
     */
    void putOnlineUser(Long userId);

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    boolean isOnline(Long userId);
}
