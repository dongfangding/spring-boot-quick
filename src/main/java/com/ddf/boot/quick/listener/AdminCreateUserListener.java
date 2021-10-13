package com.ddf.boot.quick.listener;

import com.ddf.boot.quick.constants.RocketMQConstants;
import com.ddf.boot.quick.features.es.mapping.EsSysUser;
import com.ddf.boot.quick.features.es.repository.EsSysUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>admin创建用户消费监听</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2021/10/13 09:49
 */
@Component
@RocketMQMessageListener(topic = RocketMQConstants.Topic.GLOBAL_TOPIC, consumeMode = ConsumeMode.CONCURRENTLY,
        consumerGroup = RocketMQConstants.ConsumerGroup.ADMIN_CREATE_USER_CONSUMER_GROUP,
        selectorExpression = RocketMQConstants.Tags.TAG_ADMIN_CREATE_USER)
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AdminCreateUserListener implements RocketMQListener<EsSysUser> {

    private final RestHighLevelClient restHighLevelClient;

    private final EsSysUserRepository esSysUserRepository;

    @SneakyThrows
    @Override
    public void onMessage(EsSysUser esUser) {
        log.info("开始消费admin创建用户数据, data = {}", esUser);
        esSysUserRepository.save(esUser);

//        final IndexRequest request = new IndexRequest();
//        request.index("sys_user").type("sys_user").id("rest_" + esUser.getUserId()).source(JsonUtil.toJson(esUser), XContentType.JSON);
//        restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }
}
