package com.sixsixsix516.im.server.zookeeper;

import com.google.gson.Gson;
import com.sixsixsix516.im.server.node.ClusterNodeWorker;
import com.sixsixsix516.im.server.node.ImNode;
import com.sixsixsix516.im.server.node.NodeConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 监听器
 *
 * @author SUN
 * @date 2022/5/1
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
public class ZookeeperListener {

    private final CuratorFramework curatorFramework;

    private final ClusterNodeWorker clusterNodeWorker;


    @PostConstruct
    private void init() throws Exception {
        // 订阅节点的增加删除事件
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, NodeConstant.PATH, true);

        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            ChildData data = pathChildrenCacheEvent.getData();
            if (data == null) {
                return;
            }

            ImNode imNode = new Gson().fromJson(new String(data.getData()), ImNode.class);
            log.info("监听到/imNode节点发生变化：path:{}  node: {}", data.getPath(), imNode);

            switch (pathChildrenCacheEvent.getType()) {
                // 建立一个连接
                case CHILD_ADDED -> clusterNodeWorker.addClusterNode(imNode);

                // 节点下线？ 客户端寻找其他节点连接
                case CHILD_REMOVED -> clusterNodeWorker.removeClusterNode(imNode);

                default -> {
                }
            }
        };

        // TODO 线程池
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);

        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
    }

}
