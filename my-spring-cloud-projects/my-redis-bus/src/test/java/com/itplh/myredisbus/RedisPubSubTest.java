package com.itplh.myredisbus;

import com.itplh.myredisbus.core.DevRedisPubSubListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

public class RedisPubSubTest {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Test
    public void sub() {
        logger.info("Sub start...");
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379, 3000);
            // 客户端配置监听两个channel的消息
            jedis.subscribe(new DevRedisPubSubListener(), "channel1", "channel2");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.disconnect();
            }
        }
    }

    @Test
    public void pub() {
        logger.info("Pub start...");
        Jedis jedis = null;
        try {
            jedis = new Jedis("127.0.0.1", 6379, 3000);
            // 向channel1中发送消息
            jedis.publish("channel1", "{\"message\": \"this message from channel1.\"}");
            // 2s后向channel1中发送消息
            TimeUnit.SECONDS.sleep(2);
            jedis.publish("channel2", "hello, this message from channel2.");
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.disconnect();
            }
        }
        logger.info("Pub end...");
    }

}
