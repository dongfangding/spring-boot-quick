package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.UserMetadataConfig;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2025/12/19 11:48
 */
public interface UserMetadataConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserMetadataConfig record);

    int insertSelective(UserMetadataConfig record);

    UserMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMetadataConfig record);

    int updateByPrimaryKey(UserMetadataConfig record);

    int insertOrUpdate(UserMetadataConfig config);
}