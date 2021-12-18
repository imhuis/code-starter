package com.imhuis.code.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: imhuis
 * @date: 2021/10/6
 * @description:
 */
@Configuration
public class JedisConfiguration {

    // JedisPoolConfig extends org.apache.commons.pool2.impl.GenericObjectPoolConfig
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }

    private GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMinIdle(5);
        genericObjectPoolConfig.setMaxIdle(10);
        genericObjectPoolConfig.setMaxTotal(20);
        return genericObjectPoolConfig;
    }

    /**
     * 使用spring提供的工厂类或者jedisPool
     * @return
     */
    @Bean
    public JedisPool jedisPool(){
        return new JedisPool(jedisPoolConfig());
    }

    /**
     * 单机配置
     */
    public JedisConnectionFactory standaloneJedisConnectionFactory(JedisPoolConfig jedisPoolConfig,
                                                                   RedisStandaloneConfiguration redisStandaloneConfiguration){
        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(jedisPoolConfig)
                .build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }

    /**
     * 哨兵配置
     */
    public JedisConnectionFactory sentinelJedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration()
                .master("master")
                .sentinel("",26378)
                .sentinel("",26379);
        JedisClientConfiguration clientConfiguration = JedisClientConfiguration.builder()
                .usePooling().poolConfig(jedisPoolConfig).build();
        return new JedisConnectionFactory(redisSentinelConfiguration, clientConfiguration);
    }

    /**
     * 集群配置
     */
    public JedisConnectionFactory clusterJedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();

        return new JedisConnectionFactory(clusterConfiguration, jedisPoolConfig);
    }

}
