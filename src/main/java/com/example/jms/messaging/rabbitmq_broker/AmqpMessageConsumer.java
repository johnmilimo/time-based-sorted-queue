package com.example.jms.messaging.rabbitmq_broker;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Service;

@Service
public class AmqpMessageConsumer {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
