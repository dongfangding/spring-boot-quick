package com.ddf.boot.quickstart.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;
import com.ddf.boot.quickstart.core.mapper.PlayerMetadataConfigMapper;
import com.ddf.game.xiuxian.api.enume.PlayerConfigCodeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>玩家配置同步</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/16 15:49
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PlayerConfigRepository {

    private final PlayerMetadataConfigMapper playerMetadataConfigMapper;

    /**
     * 保存玩家配置
     *
     * @param metadataConfig
     * @return
     */
    public boolean insertOrUpdate(UserMetadataConfig metadataConfig) {
        return playerMetadataConfigMapper.insertOrUpdate(metadataConfig) > 0;
    }

    /**
     * 获取玩家指定配置
     *
     * @param playerId
     * @param playerConfigCodeEnum
     * @return
     */
    public UserMetadataConfig getConfig(Long playerId, PlayerConfigCodeEnum playerConfigCodeEnum) {
        final LambdaQueryWrapper<UserMetadataConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserMetadataConfig::getPlayerId, playerId)
                .eq(UserMetadataConfig::getConfigCode, playerConfigCodeEnum.name());
        return playerMetadataConfigMapper.selectOne(wrapper);
    }
}
