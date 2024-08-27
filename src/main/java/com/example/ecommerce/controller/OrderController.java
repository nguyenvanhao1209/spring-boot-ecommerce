package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CreateOrderRequest;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create order", description = "Create a new order")
    public ResponseEntity<OrderDto> createOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CreateOrderRequest createOrderRequest
    ){
        Long userId = ((User) userDetails).getId();
        OrderDto orderDTO = orderService.createOrder(userId, createOrderRequest);
        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all orders", description = "Get all orders")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get user orders", description = "Get orders of the current user")
    public ResponseEntity<List<OrderDto>> getUserOrders(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        Long userId = ((User) userDetails).getId();
        List<OrderDto> orders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update order status", description = "Update order status by id")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status
    ){
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }
}
