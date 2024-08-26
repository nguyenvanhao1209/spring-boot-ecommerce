package com.example.ecommerce.service;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.model.Order;

import java.util.List;

public interface OrderService {
    public OrderDto createOrder(Long userId, CreateOrderRequest createOrderRequest);
    public List<OrderDto> getAllOrders();
    public List<OrderDto> getUserOrders(Long userId);
    public OrderDto updateOrderStatus(Long orderId, Order.OrderStatus status);

}
