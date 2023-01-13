package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.GlobalMetadataConfig;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/16 14:37
*/
public interface GlobalMetadataConfigMapper extends BaseMapper<GlobalMetadataConfig> {
    int deleteByPrimaryKey(Long id);

    int insert(GlobalMetadataConfig record);

    int insertSelective(GlobalMetadataConfig record);

    GlobalMetadataConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GlobalMetadataConfig record);

    int updateByPrimaryKey(GlobalMetadataConfig record);
}
