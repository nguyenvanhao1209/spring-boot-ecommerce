package com.example.ecommerce.dto;

import com.example.ecommerce.model.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone name is required")
    private String phoneNumber;
    private Order.OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;
}
