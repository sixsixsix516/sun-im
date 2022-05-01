package com.sixsixsix516.im.server;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUN
 */
@SpringBootApplication(scanBasePackages = {"com.sixsixsix516.im"})
public class ImServerApplication implements CommandLineRunner, DisposableBean {

    public static void main(String[] args) {
        SpringApplication.run(ImServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 启动netty服务
        ImServer.start();
    }

    @Override
    public void destroy() {
        ImServer.shutdown();
    }
}
