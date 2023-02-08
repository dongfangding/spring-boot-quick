package com.ddf.boot.quickstart.core.application.listener;

import com.ddf.boot.common.core.util.BeanCopierUtils;
import com.ddf.boot.quickstart.api.event.UserLoginEventPayload;
import com.ddf.boot.quickstart.core.entity.UserLoginHistory;
import com.ddf.boot.quickstart.core.event.SysUserLoginEvent;
import com.ddf.boot.quickstart.core.mapper.UserLoginHistoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * <p>用户登录事件监听</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/01/02 00:33
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserLoginListener implements ApplicationListener<SysUserLoginEvent> {
    private final ThreadPoolTaskExecutor loginPoolTaskExecutor;
    private final UserLoginHistoryMapper userLoginHistoryMapper;

    @Override
    public void onApplicationEvent(SysUserLoginEvent event) {
        loginPoolTaskExecutor.execute(() -> {
            final UserLoginEventPayload payload = event.getPayload();
            final UserLoginHistory loginHistory = BeanCopierUtils.copy(payload, UserLoginHistory.class);
            userLoginHistoryMapper.insert(loginHistory);
        });
    }
}
