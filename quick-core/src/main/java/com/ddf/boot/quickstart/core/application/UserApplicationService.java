package com.ddf.boot.quickstart.core.application;

import com.ddf.boot.quickstart.core.repository.OnlineUserRepository;
import com.ddf.boot.quickstart.core.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>用户业务</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 14:52
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserApplicationService {
    private final UserInfoRepository userInfoRepository;
    private final OnlineUserRepository onlineUserRepository;

    public void heartBeat(Long userId) {
        // 处理心跳信息
        userInfoRepository.setHeartBeat(userId);
        // 将用户放入在线用户列表
        onlineUserRepository.putOnlineUser(userId);
    }
}
