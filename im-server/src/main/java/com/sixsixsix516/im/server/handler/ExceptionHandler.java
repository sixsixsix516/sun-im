package com.sixsixsix516.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SUN
 * @date 2022/5/1
 */
public class ExceptionHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        ctx.close();
        Channel channel = ctx.channel();
        channel.close();
        // TODO 移除掉本地存储的channel
//        channel.attr()
    }
}
