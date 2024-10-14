package com.develcode.checkout.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.develcode.checkout.model.Order;

public record CreateOrderDto(
    String customerName,
    BigDecimal amount
) {
    
}
