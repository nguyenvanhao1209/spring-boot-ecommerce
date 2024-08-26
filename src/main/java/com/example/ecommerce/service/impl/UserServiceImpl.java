package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ChangePasswordRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.UserMapper;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto registerUser(RegisterRequest registerRequest){
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create a new User object
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(User.Role.USER);

        // Save the user to the repository
        User savedUser = userRepository.save(user);

        // Convert the saved user to a UserDto and return it
        return userMapper.toDTO(savedUser);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void changePassword(String email, ChangePasswordRequest request) {
        User user = getUserByEmail(email);
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDto getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDto changeName(Long id, String newName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(newName);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Override
    public boolean checkUserId(Authentication authentication, Long id) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getId().equals(id);
    }
}
