package com.imhuis.code.redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author: imhuis
 * @date: 2021/10/24
 * @description:
 */
@SpringBootTest
public class JedisSetTest {

    Logger log = LoggerFactory.getLogger(JedisListTest.class);

    @Autowired
    public JedisPool jedisPool;

    @Test
    public void set1Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            Long len = jedis.sadd("sss", "www.google.com","www.baidu.com");
            log.info("len's {}", len);
            jedis.sismember("sss", "www.google.com");
            Long a = jedis.scard("sss");
            log.info("集合长度 {}", a);
        }finally {
            jedis.close();
        }
    }

    @Test
    public void set2Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> sss = jedis.smembers("sss");
            sss.stream().forEach(System.out::println);
        }finally {
            jedis.close();
        }
    }


    @Test
    public void set3Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.sadd("group1", "www","kkk","lll");
            jedis.sadd("group2", "www","kkk");
            Set<String> sinter = jedis.sinter("group1", "group2");
            sinter.stream().forEach(System.out::println);
        }finally {
            jedis.close();
        }
    }

}
