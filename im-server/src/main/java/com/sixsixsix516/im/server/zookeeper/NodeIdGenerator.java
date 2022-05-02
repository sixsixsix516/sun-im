package com.sixsixsix516.im.server.zookeeper;

import lombok.RequiredArgsConstructor;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Service;

/**
 * @author SUN
 * @date 2022/5/1
 */
@Service
@RequiredArgsConstructor
public class NodeIdGenerator {

    /**
     * 获取节点id
     */
    public Long get()  {
        String path = null;
        try {
            path = curatorFramework.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(NodeConstant.ID_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert path != null;
        return Long.valueOf(path.substring(NodeConstant.ID_PATH.length()));
    }

    private final CuratorFramework curatorFramework;

}
