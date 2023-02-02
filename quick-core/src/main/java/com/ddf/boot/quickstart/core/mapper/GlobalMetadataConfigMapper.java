package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.GlobalMetadataConfig;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2023/02/02 16:53
*/
public interface GlobalMetadataConfigMapper extends BaseMapper<GlobalMetadataConfig> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(GlobalMetadataConfig record);

    GlobalMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlobalMetadataConfig record);

    int updateByPrimaryKey(GlobalMetadataConfig record);
}
