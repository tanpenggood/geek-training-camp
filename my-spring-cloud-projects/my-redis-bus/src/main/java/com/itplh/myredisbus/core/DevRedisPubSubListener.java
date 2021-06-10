package com.itplh.myredisbus.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * redis发布订阅消息监听器，仅用于单元测试
 */
@Deprecated
public class DevRedisPubSubListener extends RedisPubSubListener {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void onMessage(String channel, String message) {
        logger.info("onMessage {}: {}", channel, message);
    }

}
