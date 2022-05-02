package com.sixsixsix516.im.server.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.server.session.ImSession;
import com.sixsixsix516.im.server.session.SessionContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * 聊天通道
 *
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
public class ChatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.Message message = (ImMessage.Message) msg;
        ImMessage.ChatMessage chatMessage = message.getChatMessage();
        log.info("系统收到消息 发送人：{} 目标：{} 内容：{}", chatMessage.getFromUsername(), chatMessage.getToUsername(), chatMessage.getContent());

        ImSession imSession = SessionContext.get(chatMessage.getToUsername());
        if (imSession != null) {
            // 如果对方在线，发送消息
            imSession.getChannel().writeAndFlush(message);
        }
        super.channelRead(ctx, msg);
    }

}
