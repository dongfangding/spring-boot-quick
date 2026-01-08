package com.ddf.boot.quickstart.core.repository;

import com.ddf.boot.quickstart.core.infra.mapper.SysDictMapper;
import com.ddf.boot.quickstart.core.infra.model.entity.SysDict;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>字典仓储</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2022/08/29 16:38
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class SysDictRepository  {

    private final SysDictMapper sysDictMapper;

    private final LoadingCache<String, List<SysDict>> DICT_CACHE = Caffeine.newBuilder()
            .weakValues()
            .initialCapacity(50)
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofHours(1))
            .build(this::listDictByCode);

    /**
     * 从缓存中获取字典数据
     *
     * @param dictCode
     * @return
     */
    public List<SysDict> listDictByCodeFromCache(String dictCode) {
        return DICT_CACHE.get(dictCode);
    }

    /**
     * 根据字典代码查询字典数据
     *
     * @param dictCode
     * @return
     */
    public List<SysDict> listDictByCode(String dictCode) {
        return sysDictMapper.selectByDictTypeCode(dictCode);
    }

    public void clearCache(String dictCode) {
        DICT_CACHE.invalidate(dictCode);
    }

    public void clearAllCache() {
        DICT_CACHE.invalidateAll();
    }
}
