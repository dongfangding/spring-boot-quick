package com.ddf.boot.quickstart.core.infra.repository;

import com.ddf.boot.quickstart.api.dto.UserHeartBeatDTO;
import com.ddf.boot.quickstart.core.infra.model.entity.UserInfo;

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



    /**
     * 获取用户心跳详情
     *
     * @param userId
     * @return
     */
    UserHeartBeatDTO getUserHeartBeatDetail(Long userId);


    /**
     * 设置用户心跳详情
     *
     * @param userHeartBeatDetail
     */
    void setUserHeartBeatDetail(UserHeartBeatDTO userHeartBeatDetail);

    /**
     * 增加用户每日在线时长
     *
     * @param currentTimeSeconds
     * @param userId
     * @param increaseTimeSeconds
     * @return
     */
    Double incrementDailyHeartBeat(Long currentTimeSeconds, Long userId, Double increaseTimeSeconds);


    /**
     * 累加用户连续在线时长
     *
     * @param userId
     * @param increaseTimeSeconds
     * @return
     */
    Double incrementUserContinueHeartBeat(Long userId, Double increaseTimeSeconds);

    /**
     * 设置心跳
     *
     * @param userId
     */
    void setHeartBeat(Long userId);

    /**
     * 随机取n条用户
     *
     * @param number
     * @return
     */
    UserInfo randomUser(Integer number);
}
