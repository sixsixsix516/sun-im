package com.sixsixsix516.im.server.session;

import com.sixsixsix516.im.server.cache.UserCacheService;
import com.sixsixsix516.im.server.utils.SpringApplicationContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author SUN
 * @date 2022/5/2
 */
public class SessionManager {

    /**
     * 用户下线：关闭session
     */
    public static void close(ChannelHandlerContext ctx) {
        ctx.close();

        Channel channel = ctx.channel();
        channel.close();

        // 出现异常关闭连接
        String username = channel.attr(ChannelAttribute.USERNAME).get();

        // 本地节点下载
        SessionContext.remove(username);
        // 缓存下线
        SpringApplicationContextUtil.getBean(UserCacheService.class).deleteCurrentLoginUser(username);
    }
}
