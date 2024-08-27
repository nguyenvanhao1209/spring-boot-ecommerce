package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.CartMapper;
import com.example.ecommerce.mapper.OrderMapper;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.EmailService;
import com.example.ecommerce.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final CartMapper cartMapper;
    @Override
    @Transactional
    public OrderDto createOrder(Long userId, CreateOrderRequest createOrderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found"));
        CartDto cartDto = cartService.getCart(userId);
        Cart cart = cartMapper.toEntity(cartDto);

        if(cart.getItems().isEmpty()) {
            throw new IllegalStateException("Cannot create an order with an empty cart");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(createOrderRequest.getAddress());
        order.setPhoneNumber(createOrderRequest.getPhoneNumber());
        order.setStatus(Order.OrderStatus.PREPARING);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = createOrderItems(cart, order);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userId);
        try {
            emailService.sendOrderConfirmation(savedOrder);
        } catch (Exception e){
            logger.error("Failed to send order confirmation email for order ID " + savedOrder.getId(), e);
        }
        return orderMapper.toDTO(savedOrder);
    }

    private List<OrderItem> createOrderItems(Cart cart, Order order){
        return cart.getItems().stream().map(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(()-> new EntityNotFoundException("Product not found with id: "+cartItem.getProduct().getId()));

            if(product.getQuantity() == null){
                throw new IllegalStateException("Product quantity is not set for product "+product.getName());
            }
            if(product.getQuantity() < cartItem.getQuantity()){
                throw new InsufficientStockException("Not enough stock for product "+product.getName());
            }
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);

            return new OrderItem(null, order, product, cartItem.getQuantity(), product.getPrice());
        }).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.toDTOs(orderRepository.findAll());
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderMapper.toDTOs(orderRepository.findByUserId(userId));
    }

    @Override
    public OrderDto updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new ResourceNotFoundException("Order not found with id: "+orderId));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        try {
            emailService.sendOrderUpdate(updatedOrder);
        } catch (Exception e){
            logger.error("Failed to send order update email for order ID " + updatedOrder.getId(), e);
        }
        return orderMapper.toDTO(updatedOrder);
    }
}
