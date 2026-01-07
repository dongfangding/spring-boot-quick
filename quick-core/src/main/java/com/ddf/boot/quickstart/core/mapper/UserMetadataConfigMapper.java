package com.ddf.boot.quickstart.core.mapper;

import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;
import org.apache.ibatis.annotations.Param;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface UserMetadataConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserMetadataConfig record);

    UserMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMetadataConfig record);

    int updateByPrimaryKey(UserMetadataConfig record);

    int insertOrUpdate(UserMetadataConfig config);

    UserMetadataConfig selectUserConfig(@Param("userId") Long userId, @Param("configCode") String configCode);

}
