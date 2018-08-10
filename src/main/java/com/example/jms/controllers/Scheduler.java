package com.example.jms.controllers;

import com.example.jms.entities.Email;
import com.example.jms.messaging.MessageProducer;
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
    private Gson gson;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    String home(@RequestParam("totalMessages") long totalMessages, @RequestParam("delay") long delay) {

        for(long i=0; i<=totalMessages; i++){
            Email email = new Email("john"+i, "Niaje"+i);
            messageProducer.sendEmail("mailbox", gson.toJson(email), delay);
        }
        return "Hello World!"+totalMessages;
    }
}
