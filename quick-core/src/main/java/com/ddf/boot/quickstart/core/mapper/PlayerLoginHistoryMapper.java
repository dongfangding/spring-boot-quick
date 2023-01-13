package com.ddf.boot.quickstart.core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddf.boot.quickstart.core.entity.UserLoginHistory;

/**
* <p>description</p >
*
* @author Snowball
* @version 1.0
* @date 2022/12/17 22:08
*/
public interface PlayerLoginHistoryMapper extends BaseMapper<UserLoginHistory> {
    int deleteByPrimaryKey(Long id);

    int insertSelective(UserLoginHistory record);

    UserLoginHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLoginHistory record);

    int updateByPrimaryKey(UserLoginHistory record);
}
