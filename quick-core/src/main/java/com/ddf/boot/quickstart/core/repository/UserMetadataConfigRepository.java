package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.enume.UserConfigCodeEnum;
import com.ddf.boot.quickstart.core.infra.mapper.UserMetadataConfigMapper;
import com.ddf.boot.quickstart.core.infra.model.entity.UserMetadataConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>用户配置同步</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:49
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class UserMetadataConfigRepository {

    private final UserMetadataConfigMapper userMetadataConfigMapper;

    /**
     * 保存用户配置
     *
     * @param metadataConfig
     * @return
     */
    public boolean insertOrUpdate(UserMetadataConfig metadataConfig) {
        return userMetadataConfigMapper.insertOrUpdate(metadataConfig) > 0;
    }

    /**
     * 获取用户指定配置
     *
     * @param userId
     * @param userConfigCodeEnum
     * @return
     */
    public UserMetadataConfig getConfig(Long userId, UserConfigCodeEnum userConfigCodeEnum) {
        return userMetadataConfigMapper.selectUserConfig(userId, userConfigCodeEnum.getValue());
    }
}
