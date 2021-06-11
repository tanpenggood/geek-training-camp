package com.itplh.spring.cloud.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itplh.myredisbus.core.RedisBusSubscribeReceiver;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 02:07
 */
@Configuration
public class RedisBusConfig {

    @Bean
    RedisBusSubscribeReceiver redisBusSubscribeReceiver(ServiceMatcher serviceMatcher,
                                                        ObjectProvider<BusBridge> busBridge,
                                                        BusProperties busProperties,
                                                        Destination.Factory destinationFactory,
                                                        ObjectMapper objectMapper) {
        return new RedisBusSubscribeReceiver(serviceMatcher, busBridge, busProperties, destinationFactory, objectMapper);
    }

}
