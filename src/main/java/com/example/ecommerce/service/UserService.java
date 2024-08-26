package com.example.ecommerce.service;

import com.example.ecommerce.dto.ChangePasswordRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    public UserDto registerUser(RegisterRequest registerRequest);
    public User getUserByEmail(String email);
    public void changePassword(String email, ChangePasswordRequest request);

    public List<UserDto> getAllUsers();

    public UserDto getUserById(Long id);

    public UserDto getCurrentUser();

    public UserDto changeName(Long id, String newName);

    public boolean checkUserId(Authentication authentication, Long id);
}
