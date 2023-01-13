package com.ddf.boot.quickstart.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.common.api.util.DateUtils;
import com.ddf.boot.quickstart.core.entity.UserProgress;
import com.ddf.boot.quickstart.core.mapper.PlayerProgressMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <p>玩家进度条仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/12/17 23:06
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class PlayerProgressRepository {

    private final PlayerProgressMapper playerProgressMapper;

    /**
     * 保存记录
     *
     * @param userProgress
     * @return
     */
    public int insertPlayerProgress(UserProgress userProgress) {
        return playerProgressMapper.insertSelective(userProgress);
    }

    /**
     * 获取玩家进度
     *
     * @param playerId
     * @return
     */
    public UserProgress getPlayerProgress(Long playerId) {
        final LambdaQueryWrapper<UserProgress> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserProgress::getUserId, playerId);
        return playerProgressMapper.selectOne(wrapper);
    }

    /**
     * 更新玩家最后读公告时间
     *
     * @param playerId
     * @return
     */
    public boolean updateLatestReadNoticeTime(Long playerId) {
        final LambdaUpdateWrapper<UserProgress> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(UserProgress::getUserId, playerId);
        wrapper.set(UserProgress::getLatestReadNoticeTime, DateUtils.currentTimeSeconds());
        return playerProgressMapper.update(null, wrapper) > 0;
    }
}
