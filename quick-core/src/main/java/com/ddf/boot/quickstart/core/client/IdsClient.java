package com.ddf.boot.quickstart.core.client;

import com.ddf.common.ids.service.api.IdsApi;
import com.ddf.common.ids.service.model.common.DecodeSnowflakeIdData;
import com.ddf.common.ids.service.model.common.IdsMultiData;
import com.ddf.common.ids.service.model.common.IdsMultiListData;
import com.ddf.common.ids.service.model.common.SegmentBufferView;
import com.ddf.common.ids.service.service.impl.segment.model.LeafAlloc;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>提供生成序列号</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2023/02/15 19:41
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class IdsClient {

    private final IdsApi idsApi;

    /**
     * 单个雪花id
     *
     * @return
     */
    public String getSnowflakeId() {
        return idsApi.getSnowflakeId();
    }

    /**
     * 多个雪花id
     *
     * @param number
     * @return
     */
    public List<String> getSnowflakeIds(Integer number) {
        return idsApi.getSnowflakeIds(number);
    }

    /**
     * 单个序列id
     *
     * @param key
     * @return
     */
    public String getSegmentId(String key) {
        return idsApi.getSegmentId(key);
    }

    /**
     * 多个序列id
     *
     * @param key
     * @param number
     * @return
     */
    public List<String> getSegmentIds(String key, Integer number) {
        return idsApi.getSegmentIds(key, number);
    }

    /**
     * 获取组合id
     *
     * @param key
     * @return
     */
    public IdsMultiData getMultiId(String key) {
        return idsApi.getMultiId(key);
    }

    /**
     * 批量获取组合id
     *
     * @param key
     * @param number
     * @return
     */
    public IdsMultiListData getMultiIds(String key, Integer number) {
        return idsApi.getMultiIds(key, number);
    }

    /**
     * 获取号段模式缓存信息
     *
     * @return
     */
    public Map<String, SegmentBufferView> getSegmentCache() {
        return idsApi.getSegmentCache();
    }


    /**
     * 获取号段模式db信息
     *
     * @return
     */
    public List<LeafAlloc> getDb() {
        return idsApi.getDb();
    }

    /**
     * 解析雪花id信息
     *
     */
    public DecodeSnowflakeIdData decodeSnowflakeId(String snowflakeId) {
        return idsApi.decodeSnowflakeId(snowflakeId);
    }
}
