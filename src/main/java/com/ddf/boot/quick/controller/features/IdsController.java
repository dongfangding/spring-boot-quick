package com.ddf.boot.quick.controller.features;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Id生成器</p >
 *
 * @menu Id生成器
 * @author Snowball
 * @version 1.0
 * @date 2021/07/21 13:53
 */
@RestController
@RequestMapping("ids")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class IdsController {

    private final IdsApi idsApi;

    /**
     * 基于zk的雪花id的使用方式
     *
     * @return
     */
    @GetMapping("getSnowflakeId")
    public String getSnowflakeId() {
        return idsApi.getSnowflakeId();
    }

    /**
     * 基于zk的雪花id的使用方式
     *
     * @return
     */
    @GetMapping("getSnowflakeIds")
    public List<String> getSnowflakeIds(@RequestParam Integer number) {
        return idsApi.getSnowflakeIds(number);
    }

    /**
     * 基于号段id的使用方式
     *
     * @return
     */
    @GetMapping("getSegmentId")
    public String getSegmentId(@RequestParam String key) {
        return idsApi.getSegmentId(key);
    }

    /**
     * 基于号段id的使用方式
     *
     * @return
     */
    @GetMapping("getSegmentIds")
    public List<String> getSegmentIds(@RequestParam String key, @RequestParam Integer number) {
        return idsApi.getSegmentIds(key, number);
    }


    /**
     * 获取组合id
     *
     * @param key
     * @return
     */
    @GetMapping("getMultiId")
    public IdsMultiData getMultiId(@RequestParam String key) {
        return idsApi.getMultiId(key);
    }

    /**
     * 批量获取组合id
     *
     * @param key
     * @param number
     * @return
     */
    @GetMapping("getMultiIds")
    public IdsMultiListData getMultiIds(@RequestParam String key, @RequestParam Integer number) {
        return idsApi.getMultiIds(key, number);
    }

    /**
     * 获取号段模式缓存信息
     *
     * @param model
     * @return
     */
    @GetMapping(value = "cache")
    public Map<String, SegmentBufferView> getCache(Model model) {
        return idsApi.getSegmentCache();
    }

    /**
     * 获取号段模式db信息
     *
     * @param model
     * @return
     */
    @GetMapping(value = "db")
    public List<LeafAlloc> getDb(Model model) {
        return idsApi.getDb();
    }

    /**
     * the output is like this:
     * {
     *   "timestamp": "1567733700834(2019-09-06 09:35:00.834)",
     *   "sequenceId": "3448",
     *   "workerId": "39"
     * }
     */
    @GetMapping(value = "decodeSnowflakeId")
    public DecodeSnowflakeIdData decodeSnowflakeId(@RequestParam("snowflakeId") String snowflakeIdStr) {
        return idsApi.decodeSnowflakeId(snowflakeIdStr);
    }
}
