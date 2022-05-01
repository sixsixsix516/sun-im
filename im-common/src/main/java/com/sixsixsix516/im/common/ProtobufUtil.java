package com.sixsixsix516.im.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 生成protobuf类工具
 *
 * @author SUN
 * @date 28/04/2022
 */
public class ProtobufUtil {

    public static void main(String[] args) {
        // !!! 注意，需要保证此项目所在目录不包含中文，此工具才可以正常使用

        // 要生成的proto文件(项目resource目录下)
        String protoFile = "Message.proto";
        // 输出目录
        String outputDir = "D:\\tmp";

        generate(protoFile, outputDir);
    }

    private static void generate(String protoFile, String outputDir) {
        String resourcePath = new File("im-common/src/main/resources").getAbsolutePath();
        String protocExePath = resourcePath + "\\protoc.exe";
        List<String> commandList = List.of(protocExePath,
                // 要生成的proto文件
                "--proto_path=" + resourcePath + "\\protobuf " + protoFile,
                // 输出目录
                "--java_out=" + outputDir);

        try {
            System.out.println("开始执行命令");
            System.out.println(String.join("\n", commandList));

            Process process = Runtime.getRuntime().exec(String.join(" ", commandList));

            // 打印结果
            InputStreamReader isr = new InputStreamReader(process.getErrorStream());
            BufferedReader br = new BufferedReader(isr);
            boolean isSuccess = true;
            String line;
            while ((line = br.readLine()) != null) {
                System.err.println("\n\n结果:" + line);
                isSuccess = false;
            }
            if (isSuccess) {
                System.out.println("\n\n结果：生成成功");
                // 打开目录
                Runtime.getRuntime().exec("explorer " + outputDir);
            }
            br.close();
            isr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
