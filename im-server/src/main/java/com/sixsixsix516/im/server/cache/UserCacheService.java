package com.sixsixsix516.im.server.cache;

import com.sixsixsix516.im.common.node.ImNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author SUN
 * @date 2022/5/2
 */
@RequiredArgsConstructor
@Service
public class UserCacheService {

    private final StringRedisTemplate redisTemplate;
    private final ImNode imNode;

    public void addCurrentLoginUser(String username) {
        redisTemplate.opsForHash().put(RedisKeyConstant.IM_LOGIN, username, String.valueOf(imNode.getId()));
    }

    /**
     * 获取指定用户所在的节点id
     */
    public String getImNodeId(String username) {
        Object nodeId = redisTemplate.opsForHash().get(RedisKeyConstant.IM_LOGIN, username);
        if (nodeId == null) {
            // 没在线
            return null;
        }
        return (String) nodeId;
    }

    public void deleteCurrentLoginUser(String username) {
        imNode.getConnections().decrementAndGet();
        redisTemplate.opsForHash().delete(RedisKeyConstant.IM_LOGIN, username);
        // TODO ZK回写
    }

}
