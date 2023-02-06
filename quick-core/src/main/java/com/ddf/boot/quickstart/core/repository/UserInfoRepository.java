package com.ddf.boot.quickstart.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.common.api.util.JsonUtil;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.api.dto.UserHeartBeatDTO;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.entity.UserInfo;
import com.ddf.boot.quickstart.core.mapper.UserInfoMapper;
import com.ddf.boot.quickstart.core.model.cqrs.user.CompleteUserInfoCommand;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>用户信息仓储/p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/05/21 10:53
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class UserInfoRepository {

    private final UserInfoMapper userInfoMapper;
    private final ApplicationProperties applicationProperties;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 根据userId获取用户信息
     *
     * @param userId
     * @return
     */
    public UserInfo getById(Long userId) {
        return userInfoMapper.selectById(userId);
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    public UserInfo getByMobile(String mobile) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getMobile, mobile);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 根据登录账号查询用户
     *
     * @param nickname
     * @return
     */
    public UserInfo getByAccountName(String nickname) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getNickname, nickname);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 昵称是否存在
     *
     * @param nickname
     * @return
     */
    public boolean nicknameExists(String nickname) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getNickname, nickname);
        return userInfoMapper.selectCount(wrapper) > 0;
    }


    /**
     * 根据邮箱查询用户列表， 存在多个，是因为可能邮箱都未认证
     *
     * @param email
     * @return
     */
    public List<UserInfo> listUserByEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email);
        return userInfoMapper.selectList(wrapper);
    }

    /**
     * 根据已认证的邮箱查询用户
     *
     * @param email
     * @return
     */
    public UserInfo getUserByVerifiedEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email)
                .isNotNull(UserInfo::getEmail);
        return userInfoMapper.selectOne(wrapper);
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    public boolean exitsByMobile(String mobile) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getMobile, mobile);
        return userInfoMapper.selectCount(wrapper) > 0;
    }


    /**
     * 根据邮箱查询用户
     *
     * @param email
     * @return
     */
    public boolean exitsByEmail(String email) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserInfo::getEmail, email);
        return userInfoMapper.selectCount(wrapper) > 0;
    }

    /**
     * 完善用户信息相关的更新
     *
     * @param command
     * @return
     */
    public int completeUserInfo(CompleteUserInfoCommand command) {
        final LambdaUpdateWrapper<UserInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(UserInfo::getTempEmail, command.getEmail());
        if (Objects.nonNull(command.getNickname())) {
            wrapper.set(UserInfo::getNickname, command.getNickname());
        }
        if (Objects.nonNull(command.getAvatarUrl())) {
            wrapper.set(UserInfo::getAvatarUrl, command.getAvatarUrl());
        }
        if (Objects.nonNull(command.getAvatarThumbUrl())) {
            wrapper.set(UserInfo::getAvatarThumbUrl, command.getAvatarThumbUrl());
        }
        wrapper.eq(UserInfo::getId, command.getId());
        return userInfoMapper.update(null, wrapper);
    }

    /**
     * 验证邮箱状态
     *
     * @param userId
     * @param email
     * @return
     */
    public int verifiedEmail(Long userId, String email) {
        final LambdaUpdateWrapper<UserInfo> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserInfo::getId, userId);
        wrapper.eq(UserInfo::getTempEmail, email);
        wrapper.isNull(UserInfo::getEmail);
        wrapper.set(UserInfo::getEmail, email);
        return userInfoMapper.update(null, wrapper);
    }

    /**
     * 根据用户id集合查询用户对应的列表信息
     *
     * @param uidList
     * @return
     */
    public Map<Long, UserInfo> mapListUsers(Set<Long> uidList) {
        final LambdaQueryWrapper<UserInfo> wrapper = Wrappers.lambdaQuery();
        wrapper.in(UserInfo::getId, uidList);
        final List<UserInfo> users = userInfoMapper.selectList(wrapper);
        return users.stream().collect(Collectors.toMap(UserInfo::getId, Function.identity()));
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
