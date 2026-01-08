package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.GlobalMetadataConfig;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface GlobalMetadataConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GlobalMetadataConfig record);

    GlobalMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlobalMetadataConfig record);

    int updateByPrimaryKey(GlobalMetadataConfig record);

    GlobalMetadataConfig selectByCode(String code);
}
