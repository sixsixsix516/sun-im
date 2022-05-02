package com.sixsixsix516.im.server.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.server.cache.UserCacheService;
import com.sixsixsix516.im.server.node.ClusterNodeWorker;
import com.sixsixsix516.im.server.session.ImSession;
import com.sixsixsix516.im.server.session.SessionContext;
import com.sixsixsix516.im.server.utils.SpringApplicationContextUtil;
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
        if (message.getMessageType().equals(ImMessage.MessageType.CHAT)) {

            ImMessage.ChatMessage chatMessage = message.getChatMessage();
            log.info("系统收到消息 发送人：{} 目标：{} 内容：{}", chatMessage.getFromUsername(), chatMessage.getToUsername(), chatMessage.getContent());

            String toUsername = chatMessage.getToUsername();
            ImSession imSession = SessionContext.get(toUsername);
            if (imSession == null) {
                // 如果本地节点没有对方会话，查询对方所在节点
                String imNodeId = SpringApplicationContextUtil.getBean(UserCacheService.class).getImNodeId(toUsername);
                if (imNodeId != null) {
                    // 如果对方在线，就继续消息转发，转发到所在的节点
                    log.info("开始转发节点消息");
                    SpringApplicationContextUtil.getBean(ClusterNodeWorker.class).redirectMessage(Long.valueOf(imNodeId), message);
                }

            } else {
                // 如果对方在同一节点的会话，就直接发送消息
                imSession.getChannel().writeAndFlush(message);
            }
        }
        super.channelRead(ctx, msg);
    }

}
