package com.ddf.boot.quickstart.core.repository;

import cn.hutool.core.util.StrUtil;
import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.common.api.util.JsonUtil;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.dto.EmailToken;
import com.ddf.boot.quickstart.api.dto.UserHeartBeatDTO;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * <p>通用的或者一些边缘业务的仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/28 15:20
 */
@Component
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class CommonRepository {

    private final StringRedisTemplate stringRedisTemplate;
    private final ApplicationProperties applicationProperties;

    /**
     * 设置短信验证码
     *
     * @param mobile
     * @param uuid
     * @param randomCode
     * @return
     */
    public void setSmsCode(String mobile, String uuid, String randomCode) {
        stringRedisTemplate.opsForValue().set(
                RedisKeyEnum.SMS_CODE_KEY.getKey(mobile, uuid), randomCode,
                RedisKeyEnum.SMS_CODE_KEY.getTtl());
    }

    /**
     * 获取短信验证码
     *
     * @param mobile
     * @param uuid
     * @return
     */
    public String getSmsCode(String mobile, String uuid) {
        return stringRedisTemplate.opsForValue().get(RedisKeyEnum.SMS_CODE_KEY.getKey(mobile, uuid));
    }

    /**
     * 设置邮箱激活token
     *
     * @param email
     * @param token
     * @return
     */
    public void setEmailActiveToken(String email, String token, Long userId) {
        final EmailToken emailToken = EmailToken.of(userId, email);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getKey((token)), JsonUtil.asString(emailToken), RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getTtl());
    }

    /**
     * 根据邮箱激活token获取对应用户id和邮箱
     *
     * @param token
     * @return
     */
    public EmailToken getEmailActiveToken(String token) {
        final String value = stringRedisTemplate.opsForValue().get(RedisKeyEnum.EMAIL_ACTIVE_TOKEN_KEY.getKey(token));
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return JsonUtil.toBean(value, EmailToken.class);
    }

    /**
     * 获取用户心跳详情
     *
     * @param userId
     * @return
     */
    public UserHeartBeatDTO getUserHeartBeatDetail(Long userId) {
        final String userHeartBeatDetailKey = RedisKeyEnum.USER_HEART_BEAT_DETAIL.getShardingKey(userId + "");
        final Object o = stringRedisTemplate.opsForHash().get(userHeartBeatDetailKey, userId);
        return JsonUtil.toBeanChecked(o, UserHeartBeatDTO.class);
    }


    /**
     * 设置用户心跳详情
     *
     * @param userHeartBeatDetail
     */
    public void setUserHeartBeatDetail(UserHeartBeatDTO userHeartBeatDetail) {
        final Long hashKey = userHeartBeatDetail.getUserId();
        final String userHeartBeatDetailKey = RedisKeyEnum.USER_HEART_BEAT_DETAIL.getShardingKey(hashKey + "");
        stringRedisTemplate.opsForHash().put(userHeartBeatDetailKey, hashKey, JsonUtil.toJson(userHeartBeatDetail));
    }

    /**
     * 增加用户每日在线时长
     *
     * @param currentTimeSeconds
     * @param userId
     * @param increaseTimeSeconds
     * @return
     */
    public Double incrementDailyHeartBeat(Long currentTimeSeconds, Long userId, Double increaseTimeSeconds) {
        final LocalDateTime localDateTime = DateUtils.ofSeconds(currentTimeSeconds);
        final Integer currentYearMonthDay = DateUtils.formatYearMonth(localDateTime);
        String dailyHeartBeatKey = RedisKeyEnum.DAILY_HEART_BEAT.getKey(currentYearMonthDay.toString());
        final Double score = stringRedisTemplate.opsForZSet()
                .incrementScore(dailyHeartBeatKey, userId + "", increaseTimeSeconds);
        final Long expire = stringRedisTemplate.getExpire(dailyHeartBeatKey);
        if (Objects.nonNull(expire) && expire < 0) {
            stringRedisTemplate.expire(dailyHeartBeatKey, RedisKeyEnum.DAILY_HEART_BEAT.getTtl());
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
    public Double incrementUserContinueHeartBeat(Long userId, Double increaseTimeSeconds) {
        String dailyHeartBeatKey = RedisKeyEnum.CONTINUE_HEART_BEAT.getKey();
        return stringRedisTemplate.opsForZSet().incrementScore(dailyHeartBeatKey, userId + "", increaseTimeSeconds);
    }

    /**
     * 设置心跳
     *
     * @param userId
     */
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
}
