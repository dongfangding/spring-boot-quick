package com.ddf.boot.quickstart.core.mapper;

import com.ddf.boot.quickstart.core.entity.SysDict;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface SysDictMapper  {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
}
