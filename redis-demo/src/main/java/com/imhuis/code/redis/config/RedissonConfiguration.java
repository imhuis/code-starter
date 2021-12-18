package com.imhuis.code.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: zyixh
 * @date: 2021/5/28
 * @description:
 */
@Configuration
public class RedissonConfiguration {

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://127.0.0.1:6379");
        return Redisson.create();
    }

    @Bean
    public RedissonReactiveClient redissonReactiveClient(RedissonClient redissonClient){
        RedissonReactiveClient redissonReactive = redissonClient.reactive();
//        RedissonRxClient redissonRx = redissonClient.rxJava();
        return redissonReactive;
    }
}
