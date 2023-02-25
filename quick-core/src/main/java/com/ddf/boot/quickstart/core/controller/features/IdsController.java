package com.ddf.boot.quickstart.core.controller.features;


import com.ddf.boot.common.api.model.common.response.response.ResponseData;
import com.ddf.boot.quickstart.core.client.IdsClient;
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

    private final IdsClient idsClient;

    /**
     * 基于zk的雪花id的使用方式
     *
     * @return
     */
    @GetMapping("getSnowflakeId")
    public ResponseData<String> getSnowflakeId() {
        return ResponseData.success(idsClient.getSnowflakeId());
    }

    /**
     * 基于zk的雪花id的使用方式
     *
     * @return
     */
    @GetMapping("getSnowflakeIds")
    public ResponseData<List<String>> getSnowflakeIds(@RequestParam Integer number) {
        return ResponseData.success(idsClient.getSnowflakeIds(number));
    }

    /**
     * 基于号段id的使用方式
     *
     * @return
     */
    @GetMapping("getSegmentId")
    public ResponseData<String> getSegmentId(@RequestParam String key) {
        return ResponseData.success(idsClient.getSegmentId(key));
    }

    /**
     * 基于号段id的使用方式
     *
     * @return
     */
    @GetMapping("getSegmentIds")
    public ResponseData<List<String>> getSegmentIds(@RequestParam String key, @RequestParam Integer number) {
        return ResponseData.success(idsClient.getSegmentIds(key, number));
    }


    /**
     * 获取组合id
     *
     * @param key
     * @return
     */
    @GetMapping("getMultiId")
    public ResponseData<IdsMultiData> getMultiId(@RequestParam String key) {
        return ResponseData.success(idsClient.getMultiId(key));
    }

    /**
     * 批量获取组合id
     *
     * @param key
     * @param number
     * @return
     */
    @GetMapping("getMultiIds")
    public ResponseData<IdsMultiListData> getMultiIds(@RequestParam String key, @RequestParam Integer number) {
        return ResponseData.success(idsClient.getMultiIds(key, number));
    }

    /**
     * 获取号段模式缓存信息
     *
     * @return
     */
    @GetMapping(value = "cache")
    public ResponseData<Map<String, SegmentBufferView>> getCache() {
        return ResponseData.success(idsClient.getSegmentCache());
    }

    /**
     * 获取号段模式db信息
     *
     * @return
     */
    @GetMapping(value = "db")
    public ResponseData<List<LeafAlloc>> getDb() {
        return ResponseData.success(idsClient.getDb());
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
    public ResponseData<DecodeSnowflakeIdData> decodeSnowflakeId(@RequestParam("snowflakeId") String snowflakeIdStr) {
        return ResponseData.success(idsClient.decodeSnowflakeId(snowflakeIdStr));
    }
}
