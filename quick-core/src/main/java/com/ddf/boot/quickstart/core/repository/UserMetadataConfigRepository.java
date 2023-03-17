package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.enume.UserConfigCodeEnum;
import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;

/**
 * <p>用户配置同步</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:49
 */
public interface UserMetadataConfigRepository {


    /**
     * 保存用户配置
     *
     * @param metadataConfig
     * @return
     */
    boolean insertOrUpdate(UserMetadataConfig metadataConfig);

    /**
     * 获取用户指定配置
     *
     * @param userId
     * @param userConfigCodeEnum
     * @return
     */
    UserMetadataConfig getConfig(Long userId, UserConfigCodeEnum userConfigCodeEnum);
}
