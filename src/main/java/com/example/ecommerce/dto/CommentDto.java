package com.example.ecommerce.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotBlank(message = "Content is required")
    private String content;
    @NotNull(message = "Score is required")
    @Min(value=1, message = "Score must be at least 1")
    @Max(value=5, message = "Score must be at most 5")
    private Integer score;
    private Long userId;
}
