package com.develcode.checkout.dto;

import java.util.UUID;

import com.develcode.checkout.model.Order;

public record OrderStatusDto(
    UUID orderId,
    Order.Status status
) {
    
}
