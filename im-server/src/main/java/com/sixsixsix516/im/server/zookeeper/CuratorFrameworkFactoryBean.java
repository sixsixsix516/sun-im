package com.sixsixsix516.im.server.zookeeper;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * zookeeper客户端bean
 * @author SUN
 * @date 2022/5/1
 */
@Data
@Component
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework> {

    @Value("${zookeeper.url:localhost:2181}")
    private String url;

    @Value("${zookeeper.max-retries:3}")
    private int maxRetries;

    @Value("${zookeeper.base-sleep-time-ms:1000}")
    private int baseSleepTimeMs;

    private CuratorFramework curatorFramework;

    @PostConstruct
    private void init() {
        this.curatorFramework = CuratorFrameworkFactory.newClient(url,new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries));
        this.curatorFramework.start();
    }

    @Override
    public CuratorFramework getObject() {
        return curatorFramework;
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

}
