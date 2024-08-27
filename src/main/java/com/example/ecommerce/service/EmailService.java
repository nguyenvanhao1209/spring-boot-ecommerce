package com.example.ecommerce.service;

import com.example.ecommerce.model.Order;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    public void sendOrderConfirmation(Order order);
    @Async
    public void sendOrderUpdate(Order order);

}
