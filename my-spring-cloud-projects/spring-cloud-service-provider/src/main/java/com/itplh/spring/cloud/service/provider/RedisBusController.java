package com.itplh.spring.cloud.service.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 02:08
 */
@RestController
@RequestMapping("/bus")
public class RedisBusController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    @GetMapping("/redis/env")
    public String envValue(@RequestParam("key") String key) {
        return environment.getProperty(key, "未知");
    }

    @EventListener
    public void onAckEvent(AckRemoteApplicationEvent event) {
        logger.info("onAckEvent {}", event);
    }

}
