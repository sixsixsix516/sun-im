package com.sixsixsix516.im.client.command;

import java.util.List;

/**
 * @author SUN
 * @date 27/04/2022
 */
public class CommandContext {

    private static List<Command> commands = List.of(
            new LoginCommand()
    );

    /**
     * 根据序号获取一个命令
     */
    public static Command getByNo(Integer no) {
        if (no == null) {
            throw new NullPointerException("no cannot be null");
        }
        for (Command command : commands) {
            if (command.getNo().equals(no)) {
                return command;
            }
        }
        return null;
    }

    public static void changeCommands(Command... commandList) {
        commands = List.of(commandList);
    }


    public static void printCommandList() {
        System.out.println("-------------------------------");
        System.out.println("\t菜单列表");
        for (Command command : commands) {
            System.out.println("\t" + command.getNo() + "\t" + command.getTitle());
        }
        System.out.println("-------------------------------");
    }


}
