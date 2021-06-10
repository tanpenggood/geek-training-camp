package com.itplh.spring.cloud.service.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 01:29
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderServer {

    public static void main(String[] args) {
        SpringApplication.run(ProviderServer.class, args);
    }

}
