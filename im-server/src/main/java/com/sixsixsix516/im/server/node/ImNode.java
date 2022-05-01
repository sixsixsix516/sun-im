package com.sixsixsix516.im.server.node;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代表集群中的一个节点
 *
 * @author SUN
 * @date 2022/5/1
 */
@Data
@Component
@ConfigurationProperties(prefix = "im")
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
    private Integer connections;

}
