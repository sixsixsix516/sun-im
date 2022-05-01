package com.sixsixsix516.im.common.codec;

import com.sixsixsix516.im.common.constant.ImConstant;
import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.common.security.DynamicPassword;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 编码器
 *
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
public class ProtobufEncoder extends MessageToByteEncoder<ImMessage.Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ImMessage.Message message, ByteBuf byteBuf) {
        // 1. 写入动态口令
        byteBuf.writeShort(DynamicPassword.get());
        // 2. 写入版本号
        byteBuf.writeShort(ImConstant.VERSION);

        // TODO 消息体加密

        byte[] chatMessageBytes = message.toByteArray();
        // 3.写入消息体长度
        byteBuf.writeInt(chatMessageBytes.length);

        // 4.写入消息体
        byteBuf.writeBytes(chatMessageBytes);
    }

}
