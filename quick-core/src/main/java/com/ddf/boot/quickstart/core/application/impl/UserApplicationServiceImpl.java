package com.ddf.boot.quickstart.core.application.impl;

import com.ddf.boot.quickstart.core.application.UserApplicationService;
import com.ddf.boot.quickstart.core.infra.mapper.UserHeartBeatLogMapper;
import com.ddf.boot.quickstart.core.infra.model.entity.UserHeartBeatLog;
import com.ddf.boot.quickstart.core.infra.repository.OnlineUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>用户业务</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:52
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserApplicationServiceImpl implements UserApplicationService {
    private final OnlineUserRepository onlineUserRepository;
    private final UserHeartBeatLogMapper userHeartBeatLogMapper;

    @Override
    public void heartBeat(Long userId) {
        // 处理心跳信息
        onlineUserRepository.setHeartBeat(userId);
        // 将用户放入在线用户列表
        onlineUserRepository.putOnlineUser(userId);

        final UserHeartBeatLog heartBeatLog = new UserHeartBeatLog();
        heartBeatLog.setUid(userId);
        heartBeatLog.setCtime(System.currentTimeMillis());
        userHeartBeatLogMapper.insertSelective(heartBeatLog);
    }
}
