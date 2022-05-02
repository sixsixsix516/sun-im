package com.sixsixsix516.im.server.handler;

import com.sixsixsix516.im.server.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SUN
 * @date 2022/5/1
 */
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();

        SessionManager.close(ctx);
    }
}
