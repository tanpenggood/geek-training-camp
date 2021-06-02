package com.itplh.config_center.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author: tanpenggood
 * @date: 2021-06-03 00:18
 */
@Component
public class ReadConfigAtFixedRateRunner implements CommandLineRunner {

    private Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) throws Exception {
        log.info("spring.application.name={}", environment.getProperty("spring.application.name"));
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("application.name={}", environment.getProperty("application.name"));
        }, 0L, 2L, TimeUnit.SECONDS);
    }
}
