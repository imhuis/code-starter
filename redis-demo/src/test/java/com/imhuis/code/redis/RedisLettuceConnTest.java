package com.imhuis.code.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: imhuis
 * @date: 2021/10/16
 * @description:
 */
@SpringBootTest
public class RedisLettuceConnTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void connTest(){
        RedisURI redisURI = new RedisURI();
        RedisClient redisClient = RedisClient.create(RedisURI.create("127.0.0.1", 6379));
        StatefulRedisConnection<String,String> connection = redisClient.connect();
        RedisCommands<String,String> redisCommand = connection.sync();
        redisCommand.set("key", "Hello, Redis!");
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void test(){
    }
}
