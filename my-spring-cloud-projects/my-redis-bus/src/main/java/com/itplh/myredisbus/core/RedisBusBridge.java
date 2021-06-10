package com.itplh.myredisbus.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.StreamBusBridge;
import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.ResourceBundle;

import static com.itplh.myredisbus.core.IConstant.BUS_REDIS_CHANNEL;

public class RedisBusBridge extends StreamBusBridge {

    private Logger logger = LogManager.getLogger(this.getClass());

    private ObjectMapper objectMapper = new ObjectMapper();

    public RedisBusBridge(StreamBridge streamBridge, BusProperties properties) {
        super(streamBridge, properties);
    }

    @Override
    public void send(RemoteApplicationEvent event) {
        logger.info("send {}", event);
        ResourceBundle bundle = ResourceBundle.getBundle("META-INF/redis");
        Map<String, String> map = ((EnvironmentChangeRemoteApplicationEvent) event).getValues();
        Jedis jedis = null;
        try {
            jedis = new Jedis(bundle.getString("redis.host"),
                    Integer.parseInt(bundle.getString("redis.port")),
                    Integer.parseInt(bundle.getString("redis.timeout")));
            // 发送数据到指定channel
            jedis.publish(BUS_REDIS_CHANNEL, objectMapper.writeValueAsString(map));
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.disconnect();
            }
        }
    }

}
