package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.core.entity.SysDict;
import java.util.List;

/**
 * <p>字典仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/08/29 16:38
 */
public interface SysDictRepository {


    /**
     * 从缓存中获取字典数据
     *
     * @param dictCode
     * @return
     */
    List<SysDict> listDictByCodeFromCache(String dictCode);

    /**
     * 根据字典代码查询字典数据
     *
     * @param dictCode
     * @return
     */
    List<SysDict> listDictByCode(String dictCode);
}
