package com.develcode.checkout.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.develcode.checkout.dto.OrderStatusDto;
import com.develcode.checkout.model.Order;
import com.develcode.checkout.repository.OrderRepository;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(UUID.fromString("45fee5b2-3d58-43f1-bcbd-1ae329c7a715"));
        testOrder.setStatus(Order.Status.PENDING);
    }

    @Test
    void createOrder_ShouldSetStatusToPendingAndSave() {
        Order inputOrder = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.createOrder(inputOrder);

        assertEquals(Order.Status.PENDING, result.getStatus());
        verify(orderRepository).save(inputOrder);
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatusAndSave() {
        UUID orderId = UUID.fromString("45fee5b2-3d58-43f1-bcbd-1ae329c7a715");
        Order.Status newStatus = Order.Status.PAID;
        OrderStatusDto orderStatusDto = new OrderStatusDto(orderId, newStatus);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        orderService.updateOrderStatus(orderStatusDto);

        assertEquals(newStatus, testOrder.getStatus());
        verify(orderRepository).findById(orderId);
        verify(orderRepository).save(testOrder);
    }

    @Test
    void updateOrderStatus_ShouldThrowExceptionWhenOrderNotFound() {
        UUID orderId = UUID.fromString("45fee5b2-3d58-43f1-bcbd-1ae329c7a715");
        OrderStatusDto orderStatusDto = new OrderStatusDto(orderId, Order.Status.PAID);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.updateOrderStatus(orderStatusDto));
        verify(orderRepository).findById(orderId);
        verify(orderRepository, never()).save(any(Order.class));
    }
}