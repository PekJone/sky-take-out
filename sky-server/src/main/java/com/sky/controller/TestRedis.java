package com.sky.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2025-05-05  16:23
 */
@RestController
@RequestMapping("/admin/category")
@Slf4j
public class TestRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/redis")
    public String test(@RequestParam String name, @RequestParam int age){
       ValueOperations valueOperations = redisTemplate.opsForValue();
       valueOperations.set("name","wangpf");
       System.out.println(valueOperations.get("name"));
       return name+age;
    }

    @GetMapping("/hash")
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("user","name","zhangSan");
        hashOperations.put("user","age",18);
        hashOperations.put("user","gender","男");

        String name = (String) hashOperations.get("user", "name");
        System.out.println(name);

        Set user = hashOperations.keys("user");
        System.out.println("keys:"+user);
        List user1 = hashOperations.values("user");
        System.out.println("values:"+user1);
        Map user2 = hashOperations.entries("user");
        System.out.println("user:"+user2);



    }

    @PostMapping("/testZset")
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();

        zSetOperations.add("zset","wang",100);
        zSetOperations.add("zset","wang1",1001);
        zSetOperations.add("zset","wang2",1002);

        Set zset = zSetOperations.range("zset", 0, -1);
        System.out.println("zset"+zset);
        Set zset1 = zSetOperations.rangeWithScores("zset", 0, -1);
        System.out.println("zset"+zset1);
    }

}
