package com.sixsixsix516.im.client.sender;

import com.sixsixsix516.im.client.channel.ChannelContext;
import com.sixsixsix516.im.common.model.ImMessage;
import io.netty.channel.Channel;

/**
 * @author SUN
 * @date 30/04/2022
 */
public class LoginSender {

    public static void login(String username) {
        Channel channel = ChannelContext.get();

        ImMessage.Message message = ImMessage.Message.newBuilder()
                .setMessageType(ImMessage.MessageType.LOGIN)
                .setLoginMessage(ImMessage.LoginMessage.newBuilder().setUsername(username).build())
                .build();

        channel.writeAndFlush(message);
    }
}
