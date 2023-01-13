package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.UserMetadataConfig;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/16 14:37
*/
public interface PlayerMetadataConfigMapper extends BaseMapper<UserMetadataConfig> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserMetadataConfig record);

    UserMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMetadataConfig record);

    int updateByPrimaryKey(UserMetadataConfig record);

    /**
     * 保存或更新
     *
     * @param record
     * @return
     */
    int insertOrUpdate(UserMetadataConfig record);
}
