package com.sixsixsix516.im.server.session;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUN
 * @date 27/04/2022
 */
public class SessionContext {

    private static final Map<String, Channel> ONLINE_SESSION = new HashMap<>();


    public static void add(String username, Channel channel) {
        ONLINE_SESSION.put(username, channel);
    }

    public static Channel get(String username) {
        return ONLINE_SESSION.get(username);
    }
}
