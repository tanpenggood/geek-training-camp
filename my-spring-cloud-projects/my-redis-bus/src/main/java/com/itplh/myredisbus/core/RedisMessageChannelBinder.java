package com.itplh.myredisbus.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.Binding;
import org.springframework.cloud.stream.binder.ConsumerProperties;
import org.springframework.cloud.stream.binder.ProducerProperties;
import org.springframework.messaging.MessageChannel;

/**
 * @author: tanpenggood
 * @date: 2021-06-11 04:16
 */
public class RedisMessageChannelBinder implements Binder<MessageChannel, ConsumerProperties, ProducerProperties> {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Binding<MessageChannel> bindConsumer(String name, String group, MessageChannel inputChannel, ConsumerProperties consumerProperties) {
        logger.info("bindConsumer {} {} {}", name, group, inputChannel, consumerProperties);

        return () -> {
            logger.info("bindConsumer {} {} {}", name, group, inputChannel, consumerProperties);
        };
    }

    @Override
    public Binding<MessageChannel> bindProducer(String name, MessageChannel outputChannel, ProducerProperties producerProperties) {
        logger.info("bindProducer {} {} {}", name, outputChannel, producerProperties);

        return () -> {
            logger.info("bindProducer {} {} {}", name, outputChannel, producerProperties);
        };
    }
}
