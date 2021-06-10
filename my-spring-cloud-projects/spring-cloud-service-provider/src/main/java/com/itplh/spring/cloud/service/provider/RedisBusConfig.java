package com.itplh.spring.cloud.service.provider;

import com.itplh.myredisbus.core.RedisBusBridge;
import com.itplh.myredisbus.core.RedisMessageChannelBinder;
import com.itplh.myredisbus.core.RedisPubSubListener;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
