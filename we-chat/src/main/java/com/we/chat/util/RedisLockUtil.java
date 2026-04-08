package com.we.chat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-04-08  16:38
 */
@Component
@RequiredArgsConstructor
public class RedisLockUtil {
     private final StringRedisTemplate stringRedisTemplate;

     /**
      * 加锁（setnx）
      */
     public boolean lock(String key, long expireSeconds) {
          return Boolean.TRUE.equals(
                  stringRedisTemplate.opsForValue()
                          .setIfAbsent(key, "locked", expireSeconds, TimeUnit.SECONDS)
          );
     }

     /**
      * 解锁
      */
     public void unlock(String key) {
          stringRedisTemplate.delete(key);
     }
}
