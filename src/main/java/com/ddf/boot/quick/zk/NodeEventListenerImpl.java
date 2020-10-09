package com.ddf.boot.quick.zk;

import com.ddf.boot.zookeeper.listener.NodeEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.springframework.stereotype.Component;

/**
 * <p>description</p >
 *
 * @author Snowball
 * @version 1.0
 * @date 2020/10/09 18:03
 */
@Component
@Slf4j
public class NodeEventListenerImpl implements NodeEventListener {
    /**
     * 节点被创建事件
     * <p>
     * 当前节点创建的时候没有放入数据，如果想要放入数据，可以实现后自己放入数据
     * <p>
     * client.setData().forPath(finalPath, "要放入的数据".getBytes())
     *
     * @param client  客户端连接对象
     * @param path    被创建的节点
     * @param oldData 节点旧数据
     * @param data    节点最新数据
     */
    @Override
    public void nodeCreate(CuratorFramework client, String path, ChildData oldData, ChildData data) {
        log.info("节点【{}】创建成功", path);
    }

    /**
     * 节点数据改变事件
     *
     * @param client  客户端连接对象
     * @param path    被创建的节点
     * @param oldData 节点旧数据
     * @param data    节点最新数据
     */
    @Override
    public void nodeChange(CuratorFramework client, String path, ChildData oldData, ChildData data) {
        log.info("节点【{}】数据发生改变, 改变前数据: {}, 改变后数据: {}", path, oldData.toString(), data.toString());
    }

    /**
     * 节点被删除事件
     *
     * @param client  客户端连接对象
     * @param path    被创建的节点
     * @param oldData 节点旧数据
     * @param data    节点最新数据
     */
    @Override
    public void nodeDeleted(CuratorFramework client, String path, ChildData oldData, ChildData data) {
        log.info("节点【{}】被删除", path);
    }
}
