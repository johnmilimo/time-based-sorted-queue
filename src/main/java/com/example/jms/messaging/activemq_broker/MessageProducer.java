package com.example.jms.messaging.activemq_broker;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;


@Component
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendEmail(String destination, String message, long delay) {
        LOGGER.info("sending message='{}' to destination='{}'", message, destination);

        jmsTemplate.convertAndSend(destination, message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                return message;
            }
        });
    }

}
