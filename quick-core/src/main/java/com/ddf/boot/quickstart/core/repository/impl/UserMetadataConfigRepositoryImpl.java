package com.ddf.boot.quickstart.core.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quickstart.api.enume.UserConfigCodeEnum;
import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;
import com.ddf.boot.quickstart.core.mapper.UserMetadataConfigMapper;
import com.ddf.boot.quickstart.core.repository.UserMetadataConfigRepository;
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
public class UserMetadataConfigRepositoryImpl implements UserMetadataConfigRepository {

    private final UserMetadataConfigMapper userMetadataConfigMapper;

    /**
     * 保存用户配置
     *
     * @param metadataConfig
     * @return
     */
    @Override
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
    @Override
    public UserMetadataConfig getConfig(Long userId, UserConfigCodeEnum userConfigCodeEnum) {
        final LambdaQueryWrapper<UserMetadataConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMetadataConfig::getUserId, userId)
                .eq(UserMetadataConfig::getConfigCode, userConfigCodeEnum.name());
        return userMetadataConfigMapper.selectOne(wrapper);
    }
}
