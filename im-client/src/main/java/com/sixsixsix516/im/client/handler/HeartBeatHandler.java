package com.sixsixsix516.im.client.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author SUN
 * @date 2022/5/1
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        heartBeat(ctx);
    }

    private void heartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                // 发送心跳消息
                ImMessage.Message message = ImMessage.Message.newBuilder().setMessageType(ImMessage.MessageType.HEART_BEAT).build();
                ctx.channel().writeAndFlush(message);
                heartBeat(ctx);
            }
        }, 3, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.Message message = (ImMessage.Message) msg;
        if (message.getMessageType().equals(ImMessage.MessageType.HEART_BEAT)) {
            return;
        }

        super.channelRead(ctx, msg);
    }
}
