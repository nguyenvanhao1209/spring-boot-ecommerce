package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItems", source = "items")
    OrderDto toDTO(Order order);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "items", source = "orderItems")
    Order toEntity(OrderDto orderDTO);

    List<OrderDto> toDTOs(List<Order> orders);
    List<Order> toEntities(List<OrderDto> orderDTOS);
    @Mapping(target = "productId", source = "product.id")
    OrderItemDto toOrderItemDTO(OrderItem orderItem);
    @Mapping(target = "product.id", source = "productId")
    OrderItem toOrderItemEntity(OrderItemDto orderItemDTO);

    List<OrderItemDto> toOrderItemDTOs(List<OrderItem> orderItem);
    List<OrderItem> toOrderItemEntities(List<OrderItemDto> orderItemDTO);
}
