package com.sixsixsix516.im.server.zookeeper;

import com.google.gson.Gson;
import com.sixsixsix516.im.common.node.ImNode;
import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务负载均衡
 *
 * @author SUN
 * @date 2022/5/2
 */
@RequiredArgsConstructor
@Service
public class ServerLoadBalance {

    /**
     * 从集群中获取一个节点
     */
    public ImNode getNode() {
        try {
            List<String> nodePathList = curatorFramework.getChildren().forPath(NodeConstant.PATH);

            List<ImNode> list = nodePathList.stream().map(nodePath -> {
                byte[] content = new byte[0];
                try {
                    content = curatorFramework.getData().forPath(NodeConstant.PATH + "/" + nodePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return new Gson().fromJson(new String(content), ImNode.class);
            }).sorted(Comparator.comparingInt(imNode -> imNode.getConnections().get())).collect(Collectors.toList());

            return list.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private final CuratorFramework curatorFramework;
}
