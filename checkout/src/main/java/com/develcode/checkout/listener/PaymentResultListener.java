package com.develcode.checkout.listener;

import java.util.UUID;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.model.Order;
import com.develcode.checkout.service.NoticeService;
import com.develcode.checkout.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PaymentResultListener {

    private final OrderService orderService;
    private final NoticeService noticeService;

    @RabbitListener(queues = "${checkout.queues}")
    public void receivePaymentResult(String message) {
        OrderStatusDto orderStatus = getMessageOrderStatus(message);
        try{
            orderService.updateOrderStatus(orderStatus);
            noticeService.noticeOrderStatus(orderStatus);
        } catch (Exception e) {
            noticeService.noticeInternalError(e.getMessage());
        }
    }

    private OrderStatusDto getMessageOrderStatus(String message) {
        JSONObject jsonObject = new JSONObject(message);
        UUID orderId = UUID.fromString(jsonObject.getString("orderId"));
        Order.Status status = Order.Status.valueOf(jsonObject.getString("status"));
        return new OrderStatusDto(orderId, status);
    }
}
