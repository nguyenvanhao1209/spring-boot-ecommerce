package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartDto;

public interface CartService {
    public CartDto addToCart(Long userId, Long productId, Integer quantity);

    public CartDto getCart(Long userId);

    public void clearCart(Long userId);
}
