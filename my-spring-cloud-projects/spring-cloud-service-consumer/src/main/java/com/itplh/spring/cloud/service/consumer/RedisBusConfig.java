package com.itplh.spring.cloud.service.consumer;

import com.itplh.myredisbus.core.RedisBusBridge;
import com.itplh.myredisbus.core.RedisMessageChannelBinder;
import com.itplh.myredisbus.core.RedisPubSubListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.util.ResourceBundle;

import static com.itplh.myredisbus.core.IConstant.BUS_REDIS_CHANNEL;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 02:07
 */
@Configuration
public class RedisBusConfig {

    @Bean
    public RedisMessageChannelBinder redisMessageChannelBinder() {
        return new RedisMessageChannelBinder();
    }

    @Bean
    RedisBusBridge busBridge(StreamBridge streamBridge, BusProperties properties) {
        return new RedisBusBridge(streamBridge, properties);
    }

    @Bean
    RedisPubSubListener redisPubSubListener() {
        return new RedisPubSubListener();
    }

    @Bean("redisMessageChannelSubscribeRunner")
    CommandLineRunner redisMessageChannelSubscribeRunner() {
        return (args) -> {
            ResourceBundle bundle = ResourceBundle.getBundle("META-INF/redis");
            Jedis jedis = null;
            try {
                jedis = new Jedis(bundle.getString("redis.host"),
                        Integer.parseInt(bundle.getString("redis.port")),
                        Integer.parseInt(bundle.getString("redis.timeout")));
                // 监听channel的消息
                jedis.subscribe(redisPubSubListener(), BUS_REDIS_CHANNEL);
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.disconnect();
                }
            }
        };
    }

}
