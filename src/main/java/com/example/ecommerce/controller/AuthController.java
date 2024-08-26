package com.example.ecommerce.controller;

import com.example.ecommerce.dto.*;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.JwtService;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login/")
    public ResponseEntity<LoginRespose> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        UserDetails userDetails = userService.getUserByEmail(loginRequest.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginRespose(token));
    }

    @PostMapping("/register/")
    public ResponseEntity<UserDto> register(
            @Valid @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/change-password/")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userService.changePassword(email, changePasswordRequest);
        return ResponseEntity.ok().body("Password changed successfully");
    }
}
