package com.itplh.myredisbus.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.bus.BusBridge;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.cloud.bus.event.AckRemoteApplicationEvent;
import org.springframework.cloud.bus.event.Destination;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;
import org.springframework.cloud.bus.event.SentApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

/**
 * Redis Bus 消息接收器
 */
public class RedisBusSubscribeReceiver implements MessageListener, ApplicationContextAware {

    private Logger logger = LogManager.getLogger(this.getClass());

    private ApplicationContext applicationContext;

    private final ServiceMatcher serviceMatcher;
    private final ObjectProvider<BusBridge> busBridge;
    private final BusProperties busProperties;
    private final Destination.Factory destinationFactory;
    private final ObjectMapper objectMapper;

    public RedisBusSubscribeReceiver(ServiceMatcher serviceMatcher,
                                     ObjectProvider<BusBridge> busBridge,
                                     BusProperties busProperties,
                                     Destination.Factory destinationFactory,
                                     ObjectMapper objectMapper) {
        this.serviceMatcher = serviceMatcher;
        this.busBridge = busBridge;
        this.busProperties = busProperties;
        this.destinationFactory = destinationFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        logger.info("onMessage: {}", message);

        RemoteApplicationEvent event = null;
        try {
            event = objectMapper.readValue(message.getBody(), RemoteApplicationEvent.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (event instanceof AckRemoteApplicationEvent) {
            if (this.busProperties.getTrace().isEnabled() && !this.serviceMatcher.isFromSelf(event)) {
                this.applicationContext.publishEvent(event);
            }
            // If it's an ACK we are finished processing at this point
            return;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Received remote event from bus: " + event);
        }

        if (this.serviceMatcher.isForSelf(event)) {
            if (!this.serviceMatcher.isFromSelf(event)) {
                this.applicationContext.publishEvent(event);
            }
            if (this.busProperties.getAck().isEnabled()) {
                AckRemoteApplicationEvent ack = new AckRemoteApplicationEvent(this,
                        this.serviceMatcher.getBusId(),
                        destinationFactory.getDestination(this.busProperties.getAck().getDestinationService()),
                        event.getDestinationService(),
                        event.getId(),
                        event.getClass());
                this.busBridge.ifAvailable(bridge -> bridge.send(ack));
                this.applicationContext.publishEvent(ack);
            }
        }

        if (this.busProperties.getTrace().isEnabled()) {
            // We are set to register sent events so publish it for local consumption, irrespective of the origin
            this.applicationContext.publishEvent(new SentApplicationEvent(this,
                    event.getOriginService(),
                    event.getDestinationService(),
                    event.getId(),
                    event.getClass()));
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}

