package com.sixsixsix516.im.server.node;

import com.sixsixsix516.im.common.codec.ProtobufDecoder;
import com.sixsixsix516.im.common.codec.ProtobufEncoder;
import com.sixsixsix516.im.server.handler.LoginHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author SUN
 * @date 2022/5/1
 */
public class ClusterNodeClient {

    public static ChannelFuture getChannel(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        return bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline()
                                // 入栈流水线
                                .addLast(new ProtobufDecoder(), new LoginHandler())
                                // 出栈流水线
                                .addLast(new ProtobufEncoder());
                    }
                })
                .connect(host, port);
    }

}
