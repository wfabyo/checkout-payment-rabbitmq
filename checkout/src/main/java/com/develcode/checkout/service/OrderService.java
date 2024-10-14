package com.develcode.checkout.service;

import org.springframework.stereotype.Service;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.model.Order;
import com.develcode.checkout.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {
    
    private final OrderRepository orderRepository;

    public Order createOrder(Order order) {
        order.setStatus(Order.Status.PENDING);
        Order savedOrder = orderRepository.save(order);
        
        return savedOrder;
    }

    public void updateOrderStatus(OrderStatusDto orderStatus) {
        Order order = orderRepository.findById(orderStatus.orderId()).orElseThrow();
        order.setStatus(orderStatus.status());
        System.out.println("payment ID: "+ orderStatus.orderId().toString() +" processed as : "+ orderStatus.status().toString());
        orderRepository.save(order);
    }
}
