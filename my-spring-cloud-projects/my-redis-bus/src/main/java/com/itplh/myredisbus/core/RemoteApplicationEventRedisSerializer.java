package com.itplh.myredisbus.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class RemoteApplicationEventRedisSerializer implements RedisSerializer<RemoteApplicationEvent> {

    private final ObjectMapper objectMapper;

    public RemoteApplicationEventRedisSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public byte[] serialize(RemoteApplicationEvent event) throws SerializationException {
        if (event == null) {
            return new byte[0];
        }
        try {
            return objectMapper.writeValueAsBytes(event);
        } catch (JsonProcessingException e) {
            throw new SerializationException("serialize fail.", e);
        }
    }

    @Override
    public RemoteApplicationEvent deserialize(byte[] bytes) throws SerializationException {
        try {
            return objectMapper.readValue(bytes, RemoteApplicationEvent.class);
        } catch (IOException e) {
            throw new DeserializationException("deserialize fail.", e);
        }
    }

}
