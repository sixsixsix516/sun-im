package com.sixsixsix516.im.common.node;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代表集群中的一个节点
 *
 * @author SUN
 * @date 2022/5/1
 */
@Data
public class ImNode {

    /**
     * 节点id：由zookeeper生成
     */
    private Long id;

    /**
     * 节点ip
     */
    private String host;

    /**
     * 节点端口
     */
    private Integer port;

    /**
     * 节点连接数
     */
    private AtomicInteger connections = new AtomicInteger(0);

}
