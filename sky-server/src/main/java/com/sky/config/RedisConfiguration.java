package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-05  16:46
 */
@Slf4j
@Configuration
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("初始化RedisTemplate对象");
        RedisTemplate redisTemplate = new RedisTemplate();
        //默认

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;

    }
}
