package com.ddf.boot.quick.listener;

import com.ddf.boot.quick.event.SysUserLoginEvent;
import com.ddf.boot.quick.features.mongo.collection.UserLoginHistoryCollection;
import com.ddf.boot.quick.features.mongo.repository.UserLoginHistoryCollectionRepository;
import com.ddf.boot.quick.model.dto.UserLoginHistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * <p>处理用户登录事件</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/02/20 14:24
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SysUserLoginListener implements ApplicationListener<SysUserLoginEvent> {

    private final UserLoginHistoryCollectionRepository userLoginHistoryCollectionRepository;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(SysUserLoginEvent event) {
        final UserLoginHistoryDTO loginHistoryDTO = event.getUserLoginHistoryDTO();
        final UserLoginHistoryCollection collection = new UserLoginHistoryCollection();
        collection.setUserId(loginHistoryDTO.getUserId());
        collection.setLoginName(loginHistoryDTO.getLoginName());
        collection.setToken(loginHistoryDTO.getToken());
        collection.setLoginTime(loginHistoryDTO.getLoginTime());
        collection.setLoginIp(loginHistoryDTO.getLoginIp());
        collection.setLoginAddress(loginHistoryDTO.getLoginAddress());
        userLoginHistoryCollectionRepository.save(collection);
    }
}
