package com.sixsixsix516.im.server.web;

import com.sixsixsix516.im.common.node.ImNode;
import com.sixsixsix516.im.server.zookeeper.ServerLoadBalance;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUN
 * @date 2022/5/2
 */
@RequiredArgsConstructor
@RestController
public class ServerController {

    @GetMapping("/getNode")
    public ImNode getNode() {
        return serverLoadBalance.getNode();
    }

    private final ServerLoadBalance serverLoadBalance;
}
