package com.ddf.boot.quick.netty.broker;

import cn.hutool.core.thread.ThreadUtil;
import com.ddf.boot.netty.broker.server.BrokerServer;
import com.ddf.boot.netty.broker.server.properties.BrokerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动netty服务端
 *
 * @author dongfang.ding
 * @date 2020/9/21 0021 23:32
 */
@Component
public class BrokerStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private BrokerProperties brokerProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ThreadUtil.newSingleExecutor()
                .execute(() -> {
                    BrokerServer brokerServer = new BrokerServer(brokerProperties);
                    brokerServer.start();
                });
    }
}
