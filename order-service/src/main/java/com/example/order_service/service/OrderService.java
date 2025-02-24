package com.example.order_service.service;
import com.example.order_service.model.dto.OrderDto;
import com.example.order_service.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto  createOrder(OrderDto orderDto);
    OrderDto getOrderById(UUID orderId);
    OrderDto updateOrderStatus(UUID orderId, String status);
    Page<OrderDto> getAllOrder(Pageable pageable);
    Page<OrderDto> getAllOrderByStatus(Pageable page, String orderStatus);
    List<OrderDto> getOrderByCustomerId(UUID customerId);
    void deleteOrder(UUID orderId);
    void cancelOrder(UUID orderId);
}
