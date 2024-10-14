package com.develcode.checkout.service;

import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.queue.QueueProducer;
import com.develcode.checkout.model.Order;

@SpringBootTest
class NoticeServiceTest {

    @Mock
    private QueueProducer queueProducer;

    @InjectMocks
    private NoticeService noticeService;

    private OrderStatusDto testOrderStatus;

    @BeforeEach
    void setUp() {
        testOrderStatus = new OrderStatusDto(UUID.fromString("45fee5b2-3d58-43f1-bcbd-1ae329c7a715"), Order.Status.PAID);
    }

    @Test
    void noticeInternalError_ShouldCallProduceErrorNotification() {
        String errorMessage = "Test error message";

        noticeService.noticeInternalError(errorMessage);

        verify(queueProducer).produceErrorNotification(errorMessage);
    }

    @Test
    void noticeOrderStatus_ShouldCallProduceOrderStatusNotification() {
        noticeService.noticeOrderStatus(testOrderStatus);

        verify(queueProducer).produceOrderStatusNotification(testOrderStatus);
    }

    @Test
    void noticeInternalError_ShouldHandleNullMessage() {
        noticeService.noticeInternalError(null);

        verify(queueProducer).produceErrorNotification(null);
    }

    @Test
    void noticeOrderStatus_ShouldHandleNullOrderStatus() {
        noticeService.noticeOrderStatus(null);

        verify(queueProducer).produceOrderStatusNotification(null);
    }
}