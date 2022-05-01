package com.sixsixsix516.im.client.command;

import com.sixsixsix516.im.client.channel.ChannelContext;
import com.sixsixsix516.im.client.sender.LoginSender;

import java.util.Scanner;

/**
 * @author SUN
 * @date 27/04/2022
 */
public class LoginCommand implements Command {

    @Override
    public Integer getNo() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "登录";
    }

    @Override
    public void exec(Scanner scanner) {
        System.out.print("请输入你的用户名：");
        String username = scanner.next();

        LoginSender.login(username);
        ChannelContext.username = username;

        synchronized (ChannelContext.LOGIN_LOCK) {
            try {
                ChannelContext.LOGIN_LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
