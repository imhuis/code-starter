package com.imhuis.code.redis.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author: zyixh
 * @date: 2021/5/28
 * @description:
 */
@Configuration
public class LettuceConfiguration {

    /**
     *
     * RedisConnectionFactory
     * + Jedis   {@link JedisConnectionFactory}
     * + Lettuce {@link LettuceConnectionFactory}
     */

    /**
     * 根据配置选择属性
     * @ConditionalOnProperty(
     *     name = {"spring.redis.client-type"},
     *     havingValue = "lettuce",
     *     matchIfMissing = true
     * )
     */



    /**
     * 单机配置
     */
    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory standaloneLettuceConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfiguration);
    }



    /**
     * 哨兵配置
     */
    public LettuceConnectionFactory sentinelLettuceConnectionFactory(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisSentinelConfiguration, clientConfiguration);
    }


    /**
     * 集群配置
     * redis cluster
     * 1.数据自动分片（16384个Hash Slot）
     * 注意：jedis只能从master进行数据读写
     *
     * https://www.cnblogs.com/larryzeal/p/7188687.html
     */
//    @Bean(destroyMethod = "destroy")
    public LettuceConnectionFactory clusterLettuceConnectionFactory(){
        RedisClusterConfiguration redisClusterConnection = new RedisClusterConfiguration();
        redisClusterConnection.addClusterNode(new RedisNode("redis1",26379));
        redisClusterConnection.addClusterNode(new RedisNode("redis2",26379));
        redisClusterConnection.addClusterNode(new RedisNode("redis3",26379));
        redisClusterConnection.setMaxRedirects(3);

        ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                .enablePeriodicRefresh(Duration.ofSeconds(60))
                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.ASK_REDIRECT, ClusterTopologyRefreshOptions.RefreshTrigger.UNKNOWN_NODE)
                .build();

        ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)// 拓扑刷新
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .autoReconnect(true)
                .socketOptions(SocketOptions.builder().keepAlive(true).build())
                .validateClusterNodeMembership(false)// 取消校验集群节点的成员关系
                .build();

        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder()
                .clientOptions(clusterClientOptions)
                .readFrom(ReadFrom.ANY)
                .commandTimeout(Duration.ofMillis(1000))
                .poolConfig(genericObjectPoolConfig())
                .build();

        return new LettuceConnectionFactory(redisClusterConnection, clientConfiguration);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    private GenericObjectPoolConfig genericObjectPoolConfig(){
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMinIdle(5);
        genericObjectPoolConfig.setMaxIdle(10);
        genericObjectPoolConfig.setMaxTotal(20);
        return genericObjectPoolConfig;
    }

}
