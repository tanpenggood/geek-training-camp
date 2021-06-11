package com.itplh.myredisbus.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.itplh.myredisbus.core.IConstant.BUS_REDIS_CHANNEL;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 10:43
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
public class RedisBusBridgeSendAutoConfiguration {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 条件注解 @ConditionalOnBean 的正确使用姿势
     * https://blog.csdn.net/forezp/article/details/84313907
     *
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new RemoteApplicationEventRedisSerializer(objectMapper));
        return redisTemplate;
    }

    @Bean
    @ConditionalOnBean(RedisBusSubscribeReceiver.class)
    RedisMessageListenerContainer redisMessageListenerContainer(RedisBusSubscribeReceiver redisPubSubListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisPubSubListener, new PatternTopic(BUS_REDIS_CHANNEL));
        return container;
    }

    @Bean
    @ConditionalOnMissingBean(RedisBusBridge.class)
    RedisBusBridge busBridge() {
        return new RedisBusBridge(redisTemplate());
    }

}
