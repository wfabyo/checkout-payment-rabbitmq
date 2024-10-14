package com.develcode.checkout.queue.impl;

import org.json.JSONObject;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.queue.QueueProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class RabbitMQQueueProducer implements QueueProducer{

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produceErrorNotification(String message) {
        try{
            rabbitTemplate.convertAndSend("internal-error-exchange", "internal.error.notice", message);
        } catch(AmqpException e) {
            e.printStackTrace();
            //TODO: Tratar erro ao enviar notificação
        }
    }

    @Override
    public void produceOrderStatusNotification(OrderStatusDto orderStatus) {
        try{
            rabbitTemplate.convertAndSend("notice-customer-exchange", "customer.notice", JSONObject.valueToString(orderStatus));
        } catch(AmqpException e) {
            e.printStackTrace();
            //TODO: Tratar erro ao enviar notificação
        }
    }
    
}
