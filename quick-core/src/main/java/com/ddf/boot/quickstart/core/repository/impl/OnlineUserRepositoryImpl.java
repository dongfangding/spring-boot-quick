package com.ddf.boot.quickstart.core.repository.impl;

import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.quickstart.api.consts.RedisKeyEnum;
import com.ddf.boot.quickstart.core.config.properties.ApplicationProperties;
import com.ddf.boot.quickstart.core.repository.OnlineUserRepository;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>在线用户管理仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/06 20:18
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class OnlineUserRepositoryImpl implements OnlineUserRepository {

    private final RedissonClient redissonClient;
    private RMapCache<Long, String> ON_LINE_MAP;
    private final ApplicationProperties applicationProperties;



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

}
