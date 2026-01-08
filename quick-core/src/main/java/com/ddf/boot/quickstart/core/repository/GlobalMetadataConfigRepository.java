package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.enume.GlobalConfigCodeEnum;
import com.ddf.boot.quickstart.core.infra.mapper.GlobalMetadataConfigMapper;
import com.ddf.boot.quickstart.core.infra.model.entity.GlobalMetadataConfig;
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
public class GlobalMetadataConfigRepository {

    private final GlobalMetadataConfigMapper globalMetadataConfigMapper;

    private final LoadingCache<GlobalConfigCodeEnum, GlobalMetadataConfig> CONFIG_CACHE = Caffeine.newBuilder()
            .weakValues()
            .initialCapacity(50)
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofHours(1))
            .build(this::getByCode);

    public GlobalMetadataConfig getByCode(GlobalConfigCodeEnum code) {
        return globalMetadataConfigMapper.selectByCode(code.getValue());
    }

    public GlobalMetadataConfig getByCodeFromCache(GlobalConfigCodeEnum code) {
        return CONFIG_CACHE.get(code);
    }

    public void clearCache(GlobalConfigCodeEnum code) {
        CONFIG_CACHE.invalidate(code);
    }

    public void clearAllCache() {
        CONFIG_CACHE.invalidateAll();
    }
}
