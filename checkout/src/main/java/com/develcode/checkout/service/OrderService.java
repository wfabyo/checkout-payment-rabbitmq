package com.develcode.checkout.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.model.Order;
import com.develcode.checkout.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public Order createOrder(Order order) {
        order.setStatus(Order.Status.PENDING);
        Order savedOrder = orderRepository.save(order);
        
        // Enviar para fila de pagamento
        rabbitTemplate.convertAndSend("payment-exchange", "payment.process", savedOrder);
        
        return savedOrder;
    }

    public void updateOrderStatus(OrderStatusDto orderStatus) {
        Order order = orderRepository.findById(orderStatus.orderId()).orElseThrow();
        order.setStatus(orderStatus.status());
        orderRepository.save(order);
    }
}
