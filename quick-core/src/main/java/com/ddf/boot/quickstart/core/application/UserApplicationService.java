package com.ddf.boot.quickstart.core.application;

/**
 * <p>用户应用层</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:39
 */
public interface UserApplicationService {

    /**
     * 心跳
     * @param userId
     */
    void heartBeat(Long userId);
}
