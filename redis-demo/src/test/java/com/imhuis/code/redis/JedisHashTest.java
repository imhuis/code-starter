package com.imhuis.code.redis;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: imhuis
 * @date: 2021/10/23
 * @description:
 */
@SpringBootTest
public class JedisHashTest {

    Logger log = LoggerFactory.getLogger(JedisHashTest.class);

    @Autowired
    public JedisPool jedisPool;

    @Test
    public void hash1Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            Map<String,String> map = new HashMap<>();
            map.put("field1", "vvvv");
            Long result = jedis.hset("hkey", map);
            log.info("result: {}", result);
            String field = jedis.hget("hkey","field1");
            log.info("field: {}", field);

            Long len = jedis.hstrlen("hkey", "field1");
            log.info("字符串长度: {}", len);

//            Long result3 = jedis.hdel("hkey","field1","field3");
//            log.info("删除的个数: {}", result3);

        }finally {
            jedis.close();
        }

    }

    @Test
    public void hash2Test(){
        Jedis jedis = jedisPool.getResource();
        try {
            Map<String,String> map = new HashMap<>();
            map.put("apple", "苹果");
            map.put("milk", "牛奶");
            map.put("happy", "快乐");
            map.put("colo", "可乐");
            map.put("sport", "运动");
            jedis.hmset("hashkey", map);
            Map<String, String> map1 = jedis.hgetAll("hashkey");
            log.info(map1.toString());
        }finally {
            jedis.close();
        }

    }
}
