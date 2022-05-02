package com.sixsixsix516.im.server.session;

import io.netty.util.AttributeKey;

/**
 * @author SUN
 * @date 2022/5/2
 */
public interface ChannelAttribute {

    AttributeKey<String> USERNAME = AttributeKey.valueOf("username");
}
