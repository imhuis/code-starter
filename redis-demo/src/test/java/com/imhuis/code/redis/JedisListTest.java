package com.imhuis.code.redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ListPosition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/10/24
 * @description:
 */
@SpringBootTest
public class JedisListTest {

    Logger log = LoggerFactory.getLogger(JedisListTest.class);

    @Autowired
    public JedisPool jedisPool;

    @Test
    public void list1Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "kkk";
            Long len = jedis.lpush(key, "a","b","c");
            log.info("len's {}", len);
            Long len1 = jedis.lpushx(key, "a");
            log.info("len1's {}", len1);
        }finally {
            jedis.close();
        }
    }

    @Test
    public void list2Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "kkk";
            String result = jedis.lpop(key);
            log.info("result's {}", result);
            String result1 = jedis.rpop(key);
            log.info("result1's {}", result1);
        }finally {
            jedis.close();
        }
    }

    @Test
    public void list3Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            String key = "kkk";
            String result = jedis.lindex(key, 1);
            log.info("result's {}", result);
//            jedis.linsert(key, ListPosition.BEFORE, "a", "b");
        }finally {
            jedis.close();
        }
    }

}
