package com.example.jms.controllers;

import com.example.jms.messaging.activemq_broker.MessageProducer;
import com.example.jms.messaging.rabbitmq_broker.AmqpMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class Scheduler {

    @Autowired
    MessageProducer messageProducer;

    @Autowired
    AmqpMessageProducer amqpMessageProducer;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    String home(@RequestParam("totalMessages") long totalMessages, @RequestParam("delay") long delay) throws Exception {

        for(long i=0; i<=totalMessages; i++){
//            Email email = new Email("john"+i, "Niaje"+i);
//            messageProducer.sendEmail("mailbox", gson.toJson(email), delay);
            amqpMessageProducer.run(new String[]{"Hey Niaje!"});

        }
        return "Hello World!"+totalMessages;
    }
}
