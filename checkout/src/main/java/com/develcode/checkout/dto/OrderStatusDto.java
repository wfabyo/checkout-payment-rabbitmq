package com.develcode.checkout;

import java.util.UUID;

public record OrderStatusDto(
    UUID orderId,
    Order.Status status
) {
    
}
