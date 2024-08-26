package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/{id}/")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/me/")
    public UserDto getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PatchMapping("/{id}/")
    @PostAuthorize("returnObject.id == authentication.principal.id or hasRole('ADMIN')")
    public UserDto changeName(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.changeName(id, userDto.getName());
    }
}
