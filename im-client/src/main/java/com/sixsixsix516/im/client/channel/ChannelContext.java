package com.sixsixsix516.im.client.channel;

import io.netty.channel.Channel;

/**
 * 存储当前客户端相关信息
 *
 * @author SUN
 * @date 27/04/2022
 */
public class ChannelContext {

    private static Channel channel;

    public static final Object LOGIN_LOCK = new Object();

    public static String username;

    /**
     * 当前登录状态
     */
    public static boolean login;


    public static void set(Channel channel) {
        ChannelContext.channel = channel;
    }

    public static Channel get() {
        return channel;
    }
}
