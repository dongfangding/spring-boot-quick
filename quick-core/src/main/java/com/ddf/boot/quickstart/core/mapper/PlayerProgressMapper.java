package com.ddf.boot.quickstart.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.UserProgress;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/17 23:04
*/
public interface PlayerProgressMapper extends BaseMapper<UserProgress> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserProgress record);

    UserProgress selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserProgress record);

    int updateByPrimaryKey(UserProgress record);
}
