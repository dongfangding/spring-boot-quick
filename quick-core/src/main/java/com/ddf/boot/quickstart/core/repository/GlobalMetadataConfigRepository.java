package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.api.enume.GlobalConfigCodeEnum;
import com.ddf.boot.quickstart.core.entity.GlobalMetadataConfig;

/**
 * <p>全局配置</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/25 23:45
 */
public interface GlobalMetadataConfigRepository {

    /**
     * 根据code获得配置
     *
     * @param code
     * @return
     */
    GlobalMetadataConfig getByCode(GlobalConfigCodeEnum code);

    /**
     * 从缓存中获取配置
     *
     * @param code
     * @return
     */
    GlobalMetadataConfig getByCodeFromCache(GlobalConfigCodeEnum code);

    /**
     * 清除指定缓存
     *
     * @param code
     */
    void clearCache(GlobalConfigCodeEnum code);

    /**
     * 清除字典全部缓存
     */
    void clearAllCache();
}
