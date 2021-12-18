package com.imhuis.code.redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.UUID;

/**
 * @author: imhuis
 * @date: 2021/10/22
 * @description:
 */
@SpringBootTest
public class JedisStringTest {

    Logger log = LoggerFactory.getLogger(JedisStringTest.class);

    @Autowired
    public JedisPool jedisPool;

    @Test
    public void jedisTest(){
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.clientSetname(UUID.randomUUID().toString());
            log.info("jedis,client info: {}", jedis.clientInfo());
        }finally {
            jedis.close();
        }
    }

    @Test
    public void stringTest(){
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "hello";
//            String result = jedis.set("hello", "Hello, Redis!");
//            log.info("result: {}", result);
//            String value = jedis.get("hello");
//            log.info("'value' is: {}", value);

            String value2 = jedis.getSet(key, "Hello");
            log.info("'value之前的值' is: {}", value2);

            Long len = jedis.strlen(key);
            log.info("key's len: {}", len);

            Long newLen = jedis.append(key, "World");
            log.info("字符串追加后的长度：{}", newLen);

            // HelnewString

            Long newLen1 = jedis.setrange(key, 3, "newString");
            log.info("字符串设置后的长度：{}", newLen1);
            log.info("新字符串：{}", jedis.get(key));

            jedis.setex("key", 10L, "nihao");
        }finally {
            jedis.close();
        }
    }

    @Test
    public void string1Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.set("abc", "ABC", new SetParams().ex(10L));
//            String result = jedis.set("abc", "ABC", new SetParams().px(10000L));
            log.info("result: {}", result);

//            String result1 = jedis.set("abc", "ABC", new SetParams().nx());
//            log.info("result1: {}", result1);

            Long result2 = jedis.setnx("abc", "ABC");
            log.info("result2: {}", result2);

            String value = jedis.get("abc");
            log.info("'abc' is: {}", value);

            String value1 = jedis.get("abc1");
            log.info("'nil value' is: {}", value1);

        }finally {
            jedis.close();
        }
    }

    @Test
    public void string3Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.setex("a", 60L, "b");
            log.info("result : {}", result);

        }finally {
            jedis.close();
        }
    }

    @Test
    public void string4Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String pageViews = "page_view";
            Long result = jedis.incr(pageViews);
            log.info("浏览量 : {}", result);
            Long result1 = jedis.incrBy(pageViews, 2);
            log.info("浏览量 : {}", result1);


        }finally {
            jedis.close();
        }
    }

    @Test
    public void string5Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String result = jedis.mset("key1","value1","key2","value2");
            log.info("result: {}", result);
            Long result1 = jedis.msetnx("key1","value1","key2","value2");
            log.info("result1: {}", result1);
            List<String> mgetList = jedis.mget("key1","key2");
            mgetList.stream().forEach(System.out::println);

        }finally {
            jedis.close();
        }
    }

    @Test
    public void listTest(){
        Jedis jedis = jedisPool.getResource();
        try {

        }finally {
            jedis.close();
        }

    }

    public void geoTest(){
        Jedis jedis = jedisPool.getResource();
        try {

        }finally {
            jedis.close();
        }
    }

}
