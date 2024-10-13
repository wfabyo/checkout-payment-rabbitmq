package com.develcode.checkout.queue.impl;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.develcode.checkout.queue.QueueProducer;

public class RabbitMQQueueProducer implements QueueProducer{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void produceErrorNotification() {
        String json = "{\"foo\" : \"value\" }";
		Message jsonMessage = MessageBuilder.withBody(json.getBytes())
				.andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json")
				.build()).build();

		rabbitTemplate.send("${checkout.queues}", jsonMessage);
    }

    @Override
    public void produceOrderStatusNotification() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
