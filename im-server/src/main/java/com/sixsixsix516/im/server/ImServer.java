package com.sixsixsix516.im.server;

import com.sixsixsix516.im.common.codec.ProtobufDecoder;
import com.sixsixsix516.im.common.codec.ProtobufEncoder;
import com.sixsixsix516.im.server.handler.ExceptionHandler;
import com.sixsixsix516.im.server.handler.LoginHandler;
import com.sixsixsix516.im.server.node.ClusterNodeWorker;
import com.sixsixsix516.im.server.node.ImNode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
@Component
public class ImServer {

    private static NioEventLoopGroup boss;
    private static NioEventLoopGroup work;
    private static Long nodeId;

    public static void start() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        boss = new NioEventLoopGroup();
        work = new NioEventLoopGroup();

        ChannelFuture bindFuture = serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                // 入栈流水线
                                .addLast(new ProtobufDecoder(), new LoginHandler(), new ExceptionHandler())
                                // 出栈流水线
                                .addLast(new ProtobufEncoder());
                    }
                })
                .bind(localImNode.getHost(), localImNode.getPort());


        bindFuture.addListener(future -> {
            if (future.isSuccess()) {
                // 启动成功，将当前节点注册到zookeeper中
                log.info("ImServer start successful!");
                nodeId = clusterNodeWorker.createLocalNode();
            } else {
                // 失败
                log.info("ImServer start failed!");
            }
        });
    }


    public static void shutdown() {
        clusterNodeWorker.deleteLocalNode(nodeId);
        boss.shutdownGracefully();
        work.shutdownGracefully();
        log.info("ImServer shutdown!");
    }

    private static ClusterNodeWorker clusterNodeWorker;
    private static ImNode localImNode;


    @Autowired
    public void setLocalImNode(ImNode localImNode) {
        ImServer.localImNode = localImNode;
    }

    @Autowired
    public void setClusterNodeWorker(ClusterNodeWorker clusterNodeWorker) {
        ImServer.clusterNodeWorker = clusterNodeWorker;
    }
}
