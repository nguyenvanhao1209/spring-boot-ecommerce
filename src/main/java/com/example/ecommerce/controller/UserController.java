package com.example.ecommerce.controller;

import com.example.ecommerce.dto.UserDto;
import com.example.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @Operation(summary = "Get all users", description = "Get all users")
    public ResponseEntity<Page<UserDto>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @GetMapping("/{id}/")
    @Operation(summary = "Get user by id", description = "Get user by id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me/")
    @Operation(summary = "Get current user", description = "Get current user")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @PatchMapping("/{id}/")
    @Operation(summary = "Change name", description = "Change name")
    @PostAuthorize("returnObject.id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserDto> changeName(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.changeName(id, userDto.getName()));
    }
}
