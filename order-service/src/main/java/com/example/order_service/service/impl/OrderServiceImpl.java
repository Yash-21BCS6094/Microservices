package com.example.order_service.service.impl;
import com.example.order_service.config.JwtAuthenticationFilter;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.exception.ProductNotFoundException;
import com.example.order_service.exception.UserNotFoundException;
import com.example.order_service.externalService.ProductService;
import com.example.order_service.externalService.UserService;
import com.example.order_service.model.dto.*;
import com.example.order_service.model.entity.Order;
import com.example.order_service.model.entity.OrderProducts;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper,
                            ProductService productService,
                            OrderRepository orderRepository,
                            UserService userService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = new Order();
        modelMapper.map(orderDto, order);

        // Fetch all product IDs from Product Service
        List<UUID> productIds = productService.getAllProductIds();

        if (productIds == null || productIds.isEmpty()) {
            throw new ProductNotFoundException("Product service returned no products.");
        }

        // Extract product IDs from the order request
        List<OrderProductDto> orderProductDtoList = orderDto.getProducts();
        List<UUID> prodIds = orderProductDtoList.stream()
                .map(OrderProductDto::getProdId)
                .toList();

        // Validate product IDs
        List<UUID> invalidProductIds = prodIds.stream()
                .filter(id -> !productIds.contains(id))
                .toList();

        if (!invalidProductIds.isEmpty()) {
            System.out.println("Invalid product id *******");
            throw new ProductNotFoundException("Invalid product IDs: " + invalidProductIds);
        }

        // Validate User
        UUID userId = orderDto.getUserId();
        if (userId == null || userService.getUsers(userId) == null) {
            throw new UserNotFoundException("Cannot find User with ID: " + userId);
        }
        // Now I need to check the current user is the one ordering the request

        // Convert OrderProductDto list to OrderProducts entity list
        List<OrderProducts> orderProductsList = new ArrayList<>();

        for (OrderProductDto dto : orderProductDtoList) {
            OrderProducts orderProduct = new OrderProducts();
            orderProduct.setOrder(order);
            orderProduct.setProdId(dto.getProdId());
            orderProduct.setQuantity(dto.getQuantity());
            orderProductsList.add(orderProduct);
        }
        order.setId(null);
        order.setProducts(orderProductsList);
        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDto.class);
    }


    @Override
    public OrderDto getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setUserId(order.getUserId());
        User
        orderDetails.setFirstName();
        return modelMapper.map(order, OrderDto.class);
    }


    @Override
    public OrderDto updateOrderStatus(UUID orderId, String status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Cannot find order")
        );
        if(order.getStatus() != "DELIVERED"){
            order.setStatus(status);
        }
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public Page<OrderDto> getAllOrder(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(order -> modelMapper.map(order, OrderDto.class));
    }

    @Override
    public Page<OrderDto> getAllOrderByStatus(Pageable page, String orderStatus) {
        Page<Order> ordersPage = orderRepository.findByStatus(orderStatus, page);

        return (Page<OrderDto>) ordersPage.stream()
                .map(order -> modelMapper.map(order, OrderDto.class));
    }

    @Override
    public List<OrderDto> getOrderByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.findByUserId(customerId);

        return orders.stream()
                .map(ord -> modelMapper.map(ord, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Cannot find order")
        );
        orderRepository.delete(order);
    }

    @Override
    public void cancelOrder(UUID orderId) {
        updateOrderStatus(orderId, "CANCELLED");
    }
}
