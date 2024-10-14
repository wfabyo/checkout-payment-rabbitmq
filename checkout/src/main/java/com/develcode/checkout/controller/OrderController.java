package com.develcode.checkout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.develcode.checkout.dto.CreateOrderDto;
import com.develcode.checkout.model.Order;
import com.develcode.checkout.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderDto createOrder) {
        Order order = Order.builder().amount(createOrder.amount()).customerName(createOrder.customerName()).build();
        return ResponseEntity.ok(orderService.createOrder(order));
    }
}