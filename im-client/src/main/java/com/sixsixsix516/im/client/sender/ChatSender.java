package com.sixsixsix516.im.client.sender;

import com.sixsixsix516.im.client.channel.ChannelContext;
import com.sixsixsix516.im.common.model.ImMessage;
import io.netty.channel.Channel;

/**
 * @author SUN
 * @date 30/04/2022
 */
public class ChatSender {

    public static void send(String toUsername, String chatMessage) {
        Channel channel = ChannelContext.get();

        // 聊天，发送消息
        ImMessage.Message message = ImMessage.Message.newBuilder()
                .setMessageType(ImMessage.MessageType.CHAT)
                .setChatMessage(ImMessage.ChatMessage.newBuilder()
                        .setToUsername(toUsername)
                        .setFromUsername(ChannelContext.username)
                        .setContent(chatMessage)
                        .build())
                .build();

        channel.writeAndFlush(message);
    }
}
