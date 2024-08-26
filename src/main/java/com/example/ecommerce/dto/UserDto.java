package com.example.ecommerce.dto;

import com.example.ecommerce.model.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private User.Role role;
}
