package com.sixsixsix516.im.common.security;

import java.util.Random;

/**
 * 动态口令
 *
 * @author SUN
 * @date 27/04/2022
 */
//@Configuration
public class DynamicPassword {

    private static final Random RANDOM = new Random();

    private static short password = 666;

    private final int PASSWORD_LENGTH = 4;

    /**
     * 每隔三分钟生成一次动态口令
     */
//    @Scheduled(cron = "0 0/3 * * * ")
    private void generate() {
        StringBuilder randomResult = new StringBuilder();
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            randomResult.append(RANDOM.nextInt(9));
        }
        password = Short.parseShort(randomResult.toString());
    }


    /**
     * 获取当前动态口令
     */
    public static short get() {
        return password;
    }
}
