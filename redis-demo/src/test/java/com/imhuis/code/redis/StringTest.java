package com.imhuis.code.redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author: imhuis
 * @date: 2021/10/7
 * @description:
 */
@SpringBootTest
public class StringTest {

    Logger log = LoggerFactory.getLogger(StringTest.class);

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public void stringTest(){
//        BoundValueOperations bvo = redisTemplate.boundValueOps("hello");
//        bvo.set(String.valueOf(10));
//        bvo.increment(1);
//        String value1 = bvo.get().toString();

        Jedis jedis = jedisPool.getResource();
        jedis.set("hello", String.valueOf(10));
        jedis.incrBy("hello", 1);
        log.info(jedis.get("hello"));
    }

    @Test
    public void jedisTest(){
        Jedis jedis = jedisPool.getResource();
        try {
            String v = jedis.get("hello");
            log.info("数值 {}", v);
            jedis.incrBy("hello", 1);
        }finally {
            jedis.close();
        }

    }
}
