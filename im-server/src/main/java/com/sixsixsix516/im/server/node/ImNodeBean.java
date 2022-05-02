package com.sixsixsix516.im.server.node;

import com.sixsixsix516.im.common.node.ImNode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUN
 * @date 2022/5/2
 */
@Configuration
public class ImNodeBean {

    @Bean
    @ConfigurationProperties(prefix = "im")
    public ImNode imNode() {
        return new ImNode();
    }
}
