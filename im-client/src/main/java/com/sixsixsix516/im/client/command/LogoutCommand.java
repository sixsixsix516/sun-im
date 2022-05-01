package com.sixsixsix516.im.client.command;

import java.util.Scanner;

/**
 * @author SUN
 * @date 30/04/2022
 */
public class LogoutCommand implements Command {

    @Override
    public Integer getNo() {
        return 2;
    }

    @Override
    public String getTitle() {
        return "退出";
    }

    @Override
    public void exec(Scanner scanner) {

    }
}
