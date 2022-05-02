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
        redisTemplate.opsForValue().set(RedisKeyConstant.IM_LOGIN + username, String.valueOf(imNode.getId()));
    }

    public void deleteCurrentLoginUser(String username) {
        redisTemplate.delete(RedisKeyConstant.IM_LOGIN + username);
    }

}
