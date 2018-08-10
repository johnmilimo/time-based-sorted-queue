package com.example.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import javax.jms.DeliveryMode;
import org.apache.activemq.ScheduledMessage;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.ArrayList;

@EnableJms
@SpringBootApplication
public class JmsApplication {

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
													DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}

	@Bean // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	public static void main(String[] args) {

		// Launch the application
		ConfigurableApplicationContext context = SpringApplication.run(JmsApplication.class, args);

        System.out.println("Preparing messages");
		ArrayList<Email> emails = new ArrayList<>();

		// queue 50,000 messages to be processed after 20 minutes
		long totalMessages = 50000;
        for(int i=0; i<=totalMessages; i++){
            emails.add(new Email("john@jumo"+i, "1. Hello there "+i, 1200000));
        }

        // queue 5 messages to be processed after 1 minute
		emails.add(new Email("john@jumo", "1. Hello there!", 1000));
        emails.add(new Email("mwas@jumo", "2. Hello there!", 1000));
        emails.add(new Email("kiragu@jumo", "3. Hello there!", 1000));
        emails.add(new Email("wahome@jumo", "4. Hello there!", 1000));
        emails.add(new Email("wahome@jumo", "4. Hello there!", 1000));

		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        System.out.println("Sending messages");

        for (Email email : emails)
        {
            jmsTemplate.convertAndSend("mailbox", email, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws JMSException {
                    message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, email.getDeliveryDelay());
                    return message;
                }
            });
        }
        System.out.println("Done sending messages");
	}
}
