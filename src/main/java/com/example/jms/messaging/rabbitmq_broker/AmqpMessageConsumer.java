package com.example.jms.messaging.rabbitmq_broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AmqpMessageConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpMessageConsumer.class);

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
    }
}
