package com.ddf.boot.quickstart.core.infra.repository.impl;

import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.common.api.util.JsonUtil;
import com.ddf.boot.common.redis.helper.RedisCommandHelper;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.dto.UserHeartBeatDTO;
import com.ddf.boot.quickstart.core.infra.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.infra.model.entity.UserInfo;
import com.ddf.boot.quickstart.core.infra.mapper.UserInfoMapper;
import com.ddf.boot.quickstart.core.infra.repository.OnlineUserRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * <p>在线用户管理仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/06 20:18
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OnlineUserRepositoryImpl implements OnlineUserRepository {

    private final RedissonClient redissonClient;
    private RMapCache<Long, String> ON_LINE_MAP;
    private final ApplicationProperties applicationProperties;
    private final UserInfoMapper userInfoMapper;
    private final RedisCommandHelper redisCommandHelper;



    @PostConstruct
    public void init() {
        ON_LINE_MAP = redissonClient.getMapCache(RedisKeyEnum.ONLINE_USER_MAP.getKey());
    }

    /**
     * 设置在线用户
     *
     * @param userId
     */
    @Override
    public void putOnlineUser(Long userId) {
        ON_LINE_MAP.put(userId, DateUtils.currentTimeSeconds() + "", applicationProperties.getHeartBeatMaxIntervalSeconds(), TimeUnit.SECONDS);
    }

    /**
     * 用户是否在线
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isOnline(Long userId) {
        return StringUtils.isNotBlank(ON_LINE_MAP.get(userId));
    }


    /**
     * 获取用户心跳详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserHeartBeatDTO getUserHeartBeatDetail(Long userId) {
        final String userHeartBeatDetailKey = RedisKeyEnum.USER_HEART_BEAT_DETAIL.getShardingKey(userId + "");
        final Object o = redisCommandHelper.hGet(userHeartBeatDetailKey, userId.toString());
        return JsonUtil.toBeanChecked(o, UserHeartBeatDTO.class);
    }


    /**
     * 设置用户心跳详情
     *
     * @param userHeartBeatDetail
     */
    @Override
    public void setUserHeartBeatDetail(UserHeartBeatDTO userHeartBeatDetail) {
        final Long hashKey = userHeartBeatDetail.getUserId();
        final String userHeartBeatDetailKey = RedisKeyEnum.USER_HEART_BEAT_DETAIL.getShardingKey(hashKey + "");
        redisCommandHelper.hPut(userHeartBeatDetailKey, hashKey.toString(), JsonUtil.toJson(userHeartBeatDetail));
    }

    /**
     * 增加用户每日在线时长
     *
     * @param currentTimeSeconds
     * @param userId
     * @param increaseTimeSeconds
     * @return
     */
    @Override
    public Double incrementDailyHeartBeat(Long currentTimeSeconds, Long userId, Double increaseTimeSeconds) {
        final LocalDateTime localDateTime = DateUtils.ofSeconds(currentTimeSeconds);
        final Integer currentYearMonthDay = DateUtils.formatYearMonth(localDateTime);
        String dailyHeartBeatKey = RedisKeyEnum.DAILY_HEART_BEAT.getKey(currentYearMonthDay.toString());
        final Double score = redisCommandHelper.zIncrementScore(dailyHeartBeatKey, userId.toString(), increaseTimeSeconds);
        final Long expire = redisCommandHelper.getExpire(dailyHeartBeatKey);
        if (Objects.nonNull(expire) && expire < 0) {
            redisCommandHelper.expire(dailyHeartBeatKey, RedisKeyEnum.DAILY_HEART_BEAT.getTtl().getSeconds());
        }
        return score;
    }


    /**
     * 累加用户连续在线时长
     *
     * @param userId
     * @param increaseTimeSeconds
     * @return
     */
    @Override
    public Double incrementUserContinueHeartBeat(Long userId, Double increaseTimeSeconds) {
        String dailyHeartBeatKey = RedisKeyEnum.CONTINUE_HEART_BEAT.getKey();
        return redisCommandHelper.zIncrementScore(dailyHeartBeatKey, userId.toString(), increaseTimeSeconds);
    }

    /**
     * 设置心跳
     *
     * @param userId
     */
    @Override
    public void setHeartBeat(Long userId) {
        final Long currentTimeSeconds = DateUtils.currentTimeSeconds();
        UserHeartBeatDTO detail = getUserHeartBeatDetail(userId);
        // 心跳间隔时间
        final Long heartBeatIntervalSeconds = applicationProperties.getHeartBeatIntervalSeconds();
        long heartBeatIntervalPreSeconds = heartBeatIntervalSeconds;
        boolean isContinueHeartBeat = false;
        if (Objects.nonNull(detail)) {
            final Long lastUpdateTimeSeconds = detail.getLastUpdateTimeSeconds();
            heartBeatIntervalPreSeconds = currentTimeSeconds - lastUpdateTimeSeconds;
            // 预留两倍心跳时间,超过这个时间，认为没有连续在线
            if (currentTimeSeconds - lastUpdateTimeSeconds >= heartBeatIntervalSeconds * 2 + 2) {
                isContinueHeartBeat = true;
            }
        } else {
            detail = new UserHeartBeatDTO();
            detail.setUserId(userId);
        }
        detail.setLastUpdateTimeSeconds(currentTimeSeconds);
        // 重新设置用户心跳详情
        setUserHeartBeatDetail(detail);

        // 累加当日用户在线时长, 如果上一次心跳在前一天结尾，这一次心跳接收到已经到了第二天，就取两个值最小的
        long increaseHeartbeatSeconds = Math.min(heartBeatIntervalPreSeconds, DateUtils.calcPassedTodaySeconds(currentTimeSeconds));
        incrementDailyHeartBeat(currentTimeSeconds, userId, (double) increaseHeartbeatSeconds);
    }

    /**
     * 随机取n条用户
     *
     * @param number
     * @return
     */
    @Override
    public UserInfo randomUser(Integer number) {
        return userInfoMapper.randomUser(number);
    }

}
