package com.example.jms.messaging.rabbitmq_broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Service
public class AmqpMessageProducer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpMessageProducer.class);

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    TopicExchange exchange;


    public AmqpMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) {
        for (String msg : args) {
            LOGGER.info("AMQP Sending message '{}'", msg);
            rabbitTemplate.convertAndSend(exchange.getName(), "foo.bar.baz", msg);
        }

    }
}