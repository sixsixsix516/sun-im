package com.sixsixsix516.im.client.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author SUN
 * @date 27/04/2022
 */
public class ChatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.ChatMessage chatMessage = ((ImMessage.Message) msg).getChatMessage();
        System.out.println("您收到了来自" + chatMessage.getFromUsername() + "的消息：" + chatMessage.getContent());
        super.channelRead(ctx, msg);
    }
}
