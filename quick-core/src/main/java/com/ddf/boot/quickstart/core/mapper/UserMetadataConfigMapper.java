package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/02 16:53
*/
public interface UserMetadataConfigMapper extends BaseMapper<UserMetadataConfig> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserMetadataConfig record);

    UserMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMetadataConfig record);

    int updateByPrimaryKey(UserMetadataConfig record);

    int insertOrUpdate(UserMetadataConfig config);
}
