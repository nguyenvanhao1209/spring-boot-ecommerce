package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.dto.CartItemDto;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId", source = "user.id")
    CartDto toDTO(Cart Cart);
    @Mapping(target="user.id", source = "userId")
    Cart toEntity(CartDto cartDTO);

    @Mapping(target="productId", source="product.id")
    CartItemDto toDTO(CartItem cartItem);

    @Mapping(target="product.id", source="productId")
    CartItem toEntity(CartItemDto cartItemDTO);
}
