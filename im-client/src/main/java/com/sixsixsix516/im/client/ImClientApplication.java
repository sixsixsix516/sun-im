package com.sixsixsix516.im.client;

import com.sixsixsix516.im.client.channel.ChannelContext;
import com.sixsixsix516.im.client.command.Command;
import com.sixsixsix516.im.client.command.CommandContext;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * @author SUN
 */
@Slf4j
@SpringBootApplication
public class ImClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ImClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        Channel channel = ClientServer.start();
        ChannelContext.set(channel);

        Scanner scanner = new Scanner(System.in);



        while (true) {
            System.out.print("\n\n");
            CommandContext.printCommandList();
            System.out.print("请输入菜单序号：");

            String no = scanner.next();
            int select;

            try {
                select = Integer.parseInt(no);
            } catch (NumberFormatException e) {
                System.out.println("错误：请输入正确的序号");
                continue;
            }

            Command command = CommandContext.getByNo(select);
            if (command == null) {
                System.out.println("错误：请输入正确的序号");
                continue;
            }
            command.exec(scanner);
        }
    }
}
