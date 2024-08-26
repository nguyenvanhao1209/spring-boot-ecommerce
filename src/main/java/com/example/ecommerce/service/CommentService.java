package com.example.ecommerce.service;

import com.example.ecommerce.dto.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto addComment(Long productId, Long userId, CommentDto commentDTO);

    public List<CommentDto> getCommentsByProduct(Long productId);
}
