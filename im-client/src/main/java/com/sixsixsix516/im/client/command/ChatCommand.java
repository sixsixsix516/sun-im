package com.sixsixsix516.im.client.command;

import com.sixsixsix516.im.client.sender.ChatSender;

import java.util.Scanner;
import java.util.Set;

/**
 * @author SUN
 * @date 30/04/2022
 */
public class ChatCommand implements Command {

    @Override
    public Integer getNo() {
        return 1;
    }

    @Override
    public String getTitle() {
        return "聊天";
    }

    @Override
    public void exec(Scanner scanner) {

        System.out.println("开始聊天吧！");

        while (true) {
            String chatMessage = scanner.next();

            // 检测退出指令，推出当前菜单
            if (quitStrings.contains(chatMessage)) {
                break;
            }

            String[] chat = chatMessage.split(":");
            if (chat.length != 2) {
                System.out.println("请按照格式输入 用户名:聊天信息");
                continue;
            }

            String toUsername = chat[0];
            chatMessage = chat[1];

            ChatSender.send(toUsername, chatMessage);
        }
    }

    /**
     * 退出指令
     */
    private final Set<String> quitStrings = Set.of("quit", "exit");
}
