package com.develcode.checkout.queue;

import com.develcode.checkout.dto.OrderStatusDto;

public interface QueueProducer {
    void produceErrorNotification(String message);

    void produceOrderStatusNotification(OrderStatusDto orderStatusDto);
}
