package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.dto.UserHeartBeatDTO;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.model.cqrs.user.CompleteUserInfoCommand;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>用户信息仓储/p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 10:53
 */
public interface UserInfoRepository {

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfo getById(Long userId);

    /**
     * 从缓存中获取用户信息
     *
     * @param userId
     * @return
     */
    UserInfo getByIdFromCache(Long userId);

    /**
     * 获取最新用户数据，重新刷新用户缓存
     *
     * @param userId
     * @return
     */
    UserInfo refreshUserInfo(Long userId);

    /**
     * 从缓存中获取批量用户
     *
     * @param userIds
     * @return
     */
    Map<Long, UserInfo> listUserInfoMapFromCache(List<Long> userIds);

    /**
     * 从db中批量获取用户信息
     *
     * @param userIds
     * @return
     */
    Map<Long, UserInfo> listUserInfoMapFromDB(List<Long> userIds);

    /**
     * 从db中批量获取用户信息
     *
     * @param userIds
     * @return
     */
    List<UserInfo> listUserInfoFromDB(List<Long> userIds);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    UserInfo getByMobile(String mobile);

    /**
     * 根据登录账号查询用户
     *
     * @param nickname
     * @return
     */
    UserInfo getByAccountName(String nickname);

    /**
     * 昵称是否存在
     *
     * @param nickname
     * @return
     */
    boolean nicknameExists(String nickname);


    /**
     * 根据邮箱查询用户列表， 存在多个，是因为可能邮箱都未认证
     *
     * @param email
     * @return
     */
    List<UserInfo> listUserByEmail(String email);

    /**
     * 根据已认证的邮箱查询用户
     *
     * @param email
     * @return
     */
    UserInfo getUserByVerifiedEmail(String email);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    boolean exitsByMobile(String mobile);


    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    boolean exitsByEmail(String email);

    /**
     * 完善用户信息相关的更新
     *
     * @param command
     * @return
     */
    int completeUserInfo(CompleteUserInfoCommand command);

    /**
     * 验证邮箱状态
     *
     * @param userId
     * @param email
     * @return
     */
    int verifiedEmail(Long userId, String email);

    /**
     * 根据用户id集合查询用户对应的列表信息
     *
     * @param uidList
     * @return
     */
    Map<Long, UserInfo> mapListUsers(Set<Long> uidList);


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
