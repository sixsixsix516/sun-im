package com.sixsixsix516.im.server.session;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUN
 * @date 27/04/2022
 */
public class SessionContext {

    /**
     * 当前在线会话
     */
    private static final ConcurrentHashMap<String, ImSession> ONLINE_SESSION = new ConcurrentHashMap<>();

    public static void add(String username, ImSession session) {
        ONLINE_SESSION.put(username, session);
    }

    public static void remove(String username) {
        ImSession imSession = get(username);
        imSession.getChannel().close();
        ONLINE_SESSION.remove(username);
    }

    public static ImSession get(String username) {
        return ONLINE_SESSION.get(username);
    }
}
