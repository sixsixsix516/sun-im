package com.sixsixsix516.im.server.node;

import com.google.gson.Gson;
import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.common.node.ImNode;
import com.sixsixsix516.im.server.zookeeper.NodeConstant;
import com.sixsixsix516.im.server.zookeeper.NodeIdGenerator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUN
 * @date 2022/5/1
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ClusterNodeWorker {

    private final NodeIdGenerator nodeIdGenerator;
    private final CuratorFramework curatorFramework;
    private final ImNode localImNode;

    /**
     * 集群中其他节点的连接
     */
    private final ConcurrentHashMap<Long, Channel> clusterOuterNodeChannelMap = new ConcurrentHashMap<>();


    /**
     * 集群中其他节点转发消息
     */
    public void redirectMessage(Long imNodeId, ImMessage.Message message) {
        Channel channel = clusterOuterNodeChannelMap.get(imNodeId);
        if (channel != null) {
            channel.writeAndFlush(message);
        }
    }

    /**
     * 向集群中添加一个节点
     */
    public void addClusterNode(ImNode imNode) {
        if (imNode.getId().equals(localImNode.getId())) {
            return;
        }
        // 异步添加
        ChannelFuture channelFuture = ClusterNodeClient.getChannel(imNode.getHost(), imNode.getPort());
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                clusterOuterNodeChannelMap.put(imNode.getId(), channelFuture.channel());
            }
        });
    }

    /**
     * 集群中删除一个节点
     */
    public void removeClusterNode(ImNode imNode) {
        log.info("删除集群节点：{}", imNode);
        Long id = imNode.getId();

        Channel channel = clusterOuterNodeChannelMap.get(id);
        if (channel != null) {
            channel.close();
            clusterOuterNodeChannelMap.remove(id);
        }
    }

    public Long createLocalNode() {
        // 创建当前节点表示的对象
        Long nodeId = nodeIdGenerator.get();
        localImNode.setId(nodeId);
        byte[] content = new Gson().toJson(localImNode).getBytes(StandardCharsets.UTF_8);


        // 将当前节点加入到zookeeper中存储
        try {
            curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(NodeConstant.PATH + "/" + nodeId, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodeId;
    }

    public void deleteLocalNode(Long nodeId) {
        try {
            curatorFramework.delete().forPath(NodeConstant.PATH + "/" + nodeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
