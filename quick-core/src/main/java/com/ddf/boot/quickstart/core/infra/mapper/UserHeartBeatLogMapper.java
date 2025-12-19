package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.UserHeartBeatLog;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2025/12/19 11:48
*/
public interface UserHeartBeatLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserHeartBeatLog record);

    int insertSelective(UserHeartBeatLog record);

    UserHeartBeatLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserHeartBeatLog record);

    int updateByPrimaryKey(UserHeartBeatLog record);
}