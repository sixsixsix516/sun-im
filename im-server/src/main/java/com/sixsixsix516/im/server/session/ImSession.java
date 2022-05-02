package com.sixsixsix516.im.server.session;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户当前会话
 *
 * @author SUN
 * @date 27/04/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImSession {

    private String username;

    private Channel channel;

}
