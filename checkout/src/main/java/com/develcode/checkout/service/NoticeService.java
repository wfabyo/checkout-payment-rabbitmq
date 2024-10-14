package com.develcode.checkout.service;

import org.springframework.stereotype.Service;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.queue.QueueProducer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class NoticeService {
    private final QueueProducer producer;

    public void noticeInternalError(String message) {
        producer.produceErrorNotification(message);
    }

    public void noticeOrderStatus(OrderStatusDto orderStatus) {
        producer.produceOrderStatusNotification(orderStatus);
    }
}
