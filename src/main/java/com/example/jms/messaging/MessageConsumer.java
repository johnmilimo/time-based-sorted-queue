package com.example.jms.messaging;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.TextMessage;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.JMSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class MessageConsumer implements MessageListener {

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

//    private String consumerName;
//
//    public MessageConsumer(String consumerName) {
//        this.consumerName = consumerName;
//    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            logger.info("Received "
                    + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
