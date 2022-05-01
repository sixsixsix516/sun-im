package com.sixsixsix516.im.server.handler;

import com.sixsixsix516.im.common.model.ImMessage;
import com.sixsixsix516.im.server.session.SessionContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import lombok.extern.slf4j.Slf4j;

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
            SessionContext.add(username, ctx.channel());

            // 登录成功，移除登录，添加聊天
            ChannelPipeline pipeline = ctx.pipeline();
            pipeline.remove(this).addLast(new HeartBeatHandler(), new ChatHandler());

            log.info("用户" + username + " 登入系统");
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
