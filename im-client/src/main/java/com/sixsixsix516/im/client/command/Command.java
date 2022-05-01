package com.sixsixsix516.im.client.command;

import java.util.Scanner;

/**
 * @author SUN
 * @date 27/04/2022
 */
public interface Command {

    /**
     * 获取当前命令序号
     * @return 序号
     */
    Integer getNo();

    /**
     * 获取当前命令标题
     * @return 命令标题：用于显示给用户
     */
    String getTitle();

    /**
     * 用户选择命令后，执行命令
     * @param scanner 通过此参数不断的获取用户输入
     */
    void exec(Scanner scanner);
}
