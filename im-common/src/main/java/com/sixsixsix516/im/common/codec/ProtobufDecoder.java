package com.sixsixsix516.im.common.codec;

import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.common.security.DynamicPassword;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 解码器
 *
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
public class ProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 处理业务前标记下 当前位置，防止半包、粘包
        byteBuf.markReaderIndex();

        // 可读字节
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes < (2 + 2 + 4)) {
            // 动态口令：short 2个字节
            // 版本号：short 2个字节
            // 消息字长度：int 4个字节

            // 如果当前消息包不够 包头，就不做处理
            return;
        }


        // 1. 读取动态口令
        short dynamicPassword = byteBuf.readShort();
        if (dynamicPassword != DynamicPassword.get()) {
            // 动态口令不对，错误消息包
            throw new RuntimeException("The message is wrong of dynamicPassword");
        }

        // 2.读取版本号
        short version = byteBuf.readShort();

        // 3.读取消息长度
        int bodyLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < bodyLength) {
            // 消息体少了，出现了半包问题，重置读指针,下一轮再读取
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] bodyBytes = new byte[bodyLength];
        // 需要指定读取大小，因为可读字节可能会有粘包，不指定可能读取到下个包的数据
        byteBuf.readBytes(bodyBytes, 0, bodyLength);

        // 4. 反序列化
        ImMessage.Message message = ImMessage.Message.parseFrom(bodyBytes);
        list.add(message);
    }

}
