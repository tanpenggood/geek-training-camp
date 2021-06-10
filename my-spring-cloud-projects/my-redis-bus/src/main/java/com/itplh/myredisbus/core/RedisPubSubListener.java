package com.itplh.myredisbus.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.Properties;

/**
 * redis发布订阅消息监听器
 */
public class RedisPubSubListener extends JedisPubSub implements ApplicationContextAware {

    private Logger logger = LogManager.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void unsubscribe() {
        super.unsubscribe();
    }

    @Override
    public void unsubscribe(String... channels) {
        super.unsubscribe(channels);
    }

    @Override
    public void subscribe(String... channels) {
        super.subscribe(channels);
    }

    @Override
    public void psubscribe(String... patterns) {
        super.psubscribe(patterns);
    }

    @Override
    public void punsubscribe() {
        super.punsubscribe();
    }

    @Override
    public void punsubscribe(String... patterns) {
        super.punsubscribe(patterns);
    }

    @Override
    public void onMessage(String channel, String message) {
        logger.info("onMessage {}: {}", channel, message);
        Map<String, String> map = null;
        try {
            map = objectMapper.readValue(message, Map.class);
        } catch (JsonProcessingException e) {
            logger.error("RedisPubSubListener#onMessage {}", e.getMessage());
        }
        ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        Properties p = new Properties();
        for (String key : map.keySet()) {
            p.put(key, map.get(key));
        }
        propertySources.addFirst(new PropertiesPropertySource("defaultProperties", p));
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        logger.info("onPMessage {} {}: {}", pattern, channel, message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("onSubscribe {}: {}", channel, subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        logger.info("onPUnsubscribe {}: {}", pattern, subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        logger.info("onPSubscribe {}: {}", pattern, subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("onUnsubscribe {}: {}", channel, subscribedChannels);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
