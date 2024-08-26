package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CommentDto;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.CommentMapper;
import com.example.ecommerce.model.Comment;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.ecommerce.repository.CommentRepository;
import com.example.ecommerce.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto addComment(Long productId, Long userId, CommentDto commentDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setProduct(product);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByProduct(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }
}
