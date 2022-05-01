package com.sixsixsix516.im.client.handler;

import com.sixsixsix516.im.client.channel.ChannelContext;
import com.sixsixsix516.im.client.command.ChatCommand;
import com.sixsixsix516.im.client.command.CommandContext;
import com.sixsixsix516.im.client.command.LogoutCommand;
import com.sixsixsix516.im.common.model.ImMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 接收登录结果消息
 *
 * @author SUN
 * @date 30/04/2022
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ImMessage.LoginResultMessage loginResultMessage = ((ImMessage.Message) msg).getLoginResultMessage();

        boolean status = loginResultMessage.getStatus();

        // 设置当前登录状态
        ChannelContext.login = status;
        super.channelRead(ctx, msg);

        if (status) {
            System.out.println("登录成功！");
            CommandContext.changeCommands(new ChatCommand(), new LogoutCommand());

            // 登录成功，加入聊天
            HeartBeatHandler heartBeatHandler = new HeartBeatHandler();
            ctx.pipeline().remove(this).addLast(heartBeatHandler, new ChatHandler());

            heartBeatHandler.channelActive(ctx);

        } else {
            System.out.println("登录失败！");
        }

        synchronized (ChannelContext.LOGIN_LOCK) {
            ChannelContext.LOGIN_LOCK.notify();
        }
    }
}
