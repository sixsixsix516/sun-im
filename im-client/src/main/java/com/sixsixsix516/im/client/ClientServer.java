package com.sixsixsix516.im.client;

import com.sixsixsix516.im.client.handler.LoginHandler;
import com.sixsixsix516.im.client.utils.HttpUtil;
import com.sixsixsix516.im.common.codec.ProtobufDecoder;
import com.sixsixsix516.im.common.codec.ProtobufEncoder;
import com.sixsixsix516.im.common.node.ImNode;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
public class ClientServer {

    public static Channel start() throws InterruptedException {
        ImNode imNode = HttpUtil.get("http://127.0.0.1:9955/getNode", ImNode.class);
        String host = imNode.getHost();
        Integer port = imNode.getPort();


        Bootstrap bootstrap = new Bootstrap();

        ChannelFuture connectFuture = bootstrap.group(new NioEventLoopGroup())
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
                .connect(host, port).sync();

        return connectFuture.channel();
    }

}
