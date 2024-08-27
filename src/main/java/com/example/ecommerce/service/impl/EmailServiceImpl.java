package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Order;
import com.example.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Value("spring.mail.username")
    private String fromEmail;
    @Override
    public void sendOrderConfirmation(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getUser().getEmail());
            message.setSubject("Order confirmation");
            message.setText("Your order has been confirmed. Order ID " + order.getId());
            mailSender.send(message);
        }
        catch (Exception e) {
            logger.error("Failed to send order confirmation email for order ID " + order.getId(), e);
        }
    }

    @Override
    public void sendOrderUpdate(Order order) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(order.getUser().getEmail());
            message.setSubject("Order update");
            message.setText("Your order " + order.getId() + " has been " + order.getStatus());
            mailSender.send(message);
        }
        catch (Exception e) {
            logger.error("Failed to send order update email for order ID " + order.getId(), e);
        }
    }

}
