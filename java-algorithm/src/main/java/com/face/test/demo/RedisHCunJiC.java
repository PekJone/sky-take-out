package com.face.test.demo;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-27  17:57
 */
public class RedisHCunJiC{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 互斥锁解决缓存击穿
     * @param key
     * @return
     */
    public Object getData(String key){
        //先从缓存中获取数据
        Object value = redisTemplate.opsForValue().get(key);
        if(value!=null){
            return value;
        }
        //缓存中没有数据，加锁重建缓存
        synchronized (this){
            // 3. 二次检查缓存（双检锁，防止重复请求DB）
          value = redisTemplate.opsForValue().get(key);
          if(value!=null){
              return value;
          }
          //只有一个线程能走到这里
          value = getDataFromDB(key);
          redisTemplate.opsForValue().set(key,value,30, TimeUnit.MINUTES);
        }
        return value;
    }

    private Object getDataFromDB(String key) {
        return "Object";
    }

    @Autowired
    private RedissonClient redissonClient;
    //分布式锁（集群版）Redisson → 企业真正用这个
    public Object getHotData(String key){
        Object value = redisTemplate.opsForValue().get(key);
        if(value!=null){
            return value;
        }
        // 2. 缓存击穿：加分布式锁，只让一个线程回源DB
        String lockKey = "lock:cache"+key;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁，最多等待 3 秒，锁 10 秒自动释放
            boolean lockSuccess = lock.tryLock(3,10,TimeUnit.SECONDS);
            if(lockSuccess){
                try {
                    // 3. 二次检查缓存（双检锁，防止重复请求DB）
                    value = redisTemplate.opsForValue().get(key);
                    if (value != null) {
                        return value;
                    }
                    //只有一个线程能走到这里
                    value = getDataFromDB(key);
                    //写会缓存
                    redisTemplate.opsForValue().set(key, value, 30, TimeUnit.MINUTES);
                }finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return redisTemplate.opsForValue().get(key);
    }

    public static void main(String[] args) {

    }
}
