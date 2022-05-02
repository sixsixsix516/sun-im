package com.sixsixsix516.im.server.handler;

import com.google.gson.Gson;
import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.common.node.ImNode;
import com.sixsixsix516.im.server.cache.UserCacheService;
import com.sixsixsix516.im.server.session.ChannelAttribute;
import com.sixsixsix516.im.server.session.ImSession;
import com.sixsixsix516.im.server.session.SessionContext;
import com.sixsixsix516.im.server.utils.SpringApplicationContextUtil;
import com.sixsixsix516.im.server.zookeeper.NodeConstant;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.nio.charset.StandardCharsets;

/**
 * @author SUN
 * @date 27/04/2022
 */
@Slf4j
public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.LoginMessage loginMessage = ((ImMessage.Message) msg).getLoginMessage();
        super.channelRead(ctx, msg);

        // 收到用户登录消息，将通道保存起来
        String username = loginMessage.getUsername();

        boolean login = login(username);
        if (login) {
            Channel channel = ctx.channel();
            channel.attr(ChannelAttribute.USERNAME).set(username);

            SessionContext.add(username, new ImSession(username, channel));

            // 登录成功，移除登录，添加聊天
            ChannelPipeline pipeline = ctx.pipeline();
            pipeline.remove(this).addLast(new HeartBeatHandler(), new ChatHandler());

            // TODO 其他server登录系统？
            log.info("用户" + username + " 登入系统");

            // 登录成功，将当前信息存储在redis
            SpringApplicationContextUtil.getBean(UserCacheService.class).addCurrentLoginUser(username);

            // 将节点连接数+1
            ImNode imNode = SpringApplicationContextUtil.getBean(ImNode.class);
            imNode.getConnections().incrementAndGet();

            byte[] content = new Gson().toJson(imNode).getBytes(StandardCharsets.UTF_8);
            SpringApplicationContextUtil.getBean(CuratorFramework.class).setData().forPath(NodeConstant.PATH + "/" + imNode.getId(), content);
        }

        // 响应登录结果
        ImMessage.Message loginResultMessage = ImMessage.Message.newBuilder()
                .setMessageType(ImMessage.MessageType.LOGIN_RESULT)
                .setLoginResultMessage(ImMessage.LoginResultMessage.newBuilder()
                        .setStatus(login)
                        .setMessage(login ? "登录成功" : "登录失败").build())
                .build();

        ctx.channel().writeAndFlush(loginResultMessage);
    }

    /**
     * 用户登录操作
     */
    private boolean login(String username) {
//        return "SUN".equals(username);
        return true;
    }

}
