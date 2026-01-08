package com.ddf.boot.quickstart.core.infra.mapper;

import com.ddf.boot.quickstart.core.infra.model.entity.SysDict;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/18 23:43
 */
public interface SysDictMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
    /**
     * 根据字典类型查询字典明细
     *
     * @param getDictTypeCode 字典类型
     * @return 字典明细列表
     */
    List<SysDict> selectByDictTypeCode(@Param("dictTypeCode") String getDictTypeCode);
}
