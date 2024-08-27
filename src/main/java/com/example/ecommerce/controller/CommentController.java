package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CommentDto;
import com.example.ecommerce.model.User;
import com.example.ecommerce.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/product/{productId}/")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Add comment", description = "Add comment to product")
    public ResponseEntity<Object> addComment(
            @PathVariable Long productId,
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CommentDto commentDTO
    ){
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(commentService.addComment(productId, userId, commentDTO));
    }

    @GetMapping("/product/{productId}/")
    @Operation(summary = "Get comments by product", description = "Get comments by product")
    public ResponseEntity<List<CommentDto>> getCommentsByProduct(@PathVariable Long productId){
        return ResponseEntity.ok(commentService.getCommentsByProduct(productId));
    }
}
