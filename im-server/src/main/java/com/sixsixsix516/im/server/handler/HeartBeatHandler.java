package com.sixsixsix516.im.server.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.server.session.SessionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author SUN
 * @date 2022/5/1
 */
@Slf4j
public class HeartBeatHandler extends IdleStateHandler {

    public HeartBeatHandler() {
        super(1500, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.MessageType messageType = ((ImMessage.Message) msg).getMessageType();
        if (messageType.equals(ImMessage.MessageType.HEART_BEAT)) {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(msg);
                return;
            }
        }

        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        super.channelIdle(ctx, evt);
        SessionManager.close(ctx);
    }
}
