package com.ddf.boot.quickstart.core.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ddf.boot.quickstart.api.enume.GlobalConfigCodeEnum;
import com.ddf.boot.quickstart.core.entity.GlobalMetadataConfig;
import com.ddf.boot.quickstart.core.mapper.GlobalMetadataConfigMapper;
import com.ddf.boot.quickstart.core.repository.GlobalMetadataConfigRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>全局配置</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/25 23:46
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class GlobalMetadataConfigRepositoryImpl implements GlobalMetadataConfigRepository {

    private final GlobalMetadataConfigMapper globalMetadataConfigMapper;

    private final LoadingCache<GlobalConfigCodeEnum, GlobalMetadataConfig> CONFIG_CACHE = Caffeine.newBuilder()
            .weakValues()
            .initialCapacity(50)
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofHours(1))
            .build(this::getByCode);

    @Override
    public GlobalMetadataConfig getByCode(GlobalConfigCodeEnum code) {
        final LambdaQueryWrapper<GlobalMetadataConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(GlobalMetadataConfig::getConfigCode, code.name());
        return globalMetadataConfigMapper.selectOne(wrapper);
    }

    @Override
    public GlobalMetadataConfig getByCodeFromCache(GlobalConfigCodeEnum code) {
        return CONFIG_CACHE.get(code);
    }

    @Override
    public void clearCache(GlobalConfigCodeEnum code) {
        CONFIG_CACHE.invalidate(code);
    }

    @Override
    public void clearAllCache() {
        CONFIG_CACHE.invalidateAll();
    }
}
