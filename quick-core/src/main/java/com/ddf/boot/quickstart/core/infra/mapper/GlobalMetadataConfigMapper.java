package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.GlobalMetadataConfig;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */
public interface GlobalMetadataConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GlobalMetadataConfig record);

    int insertSelective(GlobalMetadataConfig record);

    GlobalMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlobalMetadataConfig record);

    int updateByPrimaryKey(GlobalMetadataConfig record);
}