package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;

public interface EmailService {
    public void sendOrderConfirmation(Order order);
    public void sendOrderUpdate(Order order);
}
