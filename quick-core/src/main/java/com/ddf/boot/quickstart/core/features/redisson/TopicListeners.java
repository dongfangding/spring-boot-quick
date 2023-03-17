package com.ddf.boot.quickstart.core.features.redisson;


import com.ddf.boot.common.redis.ext.RedisTopic;
import com.ddf.boot.quickstart.api.request.features.PublishUniqueNameDTO;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/07/19 16:04
 */
@TestComponent
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class TopicListeners {

    private final RedisTopic addUniqueTopic;

    @PostConstruct
    public void init() {
        listenerAddUniqueName();
    }

    /**
     * 监听添加新的唯一名称
     */
    public void listenerAddUniqueName() {
        addUniqueTopic.addListener(PublishUniqueNameDTO.class, (channel, data) -> {
            log.info("接收到添加新的唯一名称的消息广播，channel = {}, data = {}", channel, data);
        });
    }
}
