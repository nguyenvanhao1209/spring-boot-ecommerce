package com.example.ecommerce.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String generateToken(UserDetails userDetails);
    public Boolean validateToken(String token, UserDetails userDetails);
    public String extractUsername (String token);
}
