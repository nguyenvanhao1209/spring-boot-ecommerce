package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add to cart", description = "Add product to cart")
    public ResponseEntity<CartDto> addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                             @RequestParam Long productId,
                                             @RequestParam Integer quantity){
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(cartService.addToCart(userId, productId, quantity));
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get cart", description = "Get cart of the current user")
    public ResponseEntity<CartDto> getCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Clear cart", description = "Clear cart of the current user")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails){
        Long userId = ((User) userDetails).getId();
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
