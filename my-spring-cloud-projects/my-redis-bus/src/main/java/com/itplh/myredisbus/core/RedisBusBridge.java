package com.itplh.myredisbus.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.StreamBusBridge;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.redis.core.RedisTemplate;

import static com.itplh.myredisbus.core.IConstant.BUS_REDIS_CHANNEL;

public class RedisBusBridge extends StreamBusBridge {

    private Logger logger = LogManager.getLogger(this.getClass());

    private final RedisTemplate redisTemplate;

    public RedisBusBridge(StreamBridge streamBridge, BusProperties properties, RedisTemplate redisTemplate) {
        super(streamBridge, properties);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void send(RemoteApplicationEvent event) {
        logger.info("send {}", event);
        redisTemplate.convertAndSend(BUS_REDIS_CHANNEL, event);
    }

}
