package com.itplh.myredisbus.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.data.redis.core.RedisTemplate;

import static com.itplh.myredisbus.core.IConstant.BUS_REDIS_CHANNEL;

public class RedisBusBridge implements BusBridge {

    private Logger logger = LogManager.getLogger(this.getClass());

    private final RedisTemplate redisTemplate;

    public RedisBusBridge(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void send(RemoteApplicationEvent event) {
        logger.info("send {}", event);
        redisTemplate.convertAndSend(BUS_REDIS_CHANNEL, event);
    }

}
