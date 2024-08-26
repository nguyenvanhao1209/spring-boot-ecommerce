package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.CommentDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.model.Comment;
import com.example.ecommerce.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "image", source = "image") //add mapping
    ProductDto toDTO(Product product);

    @Mapping(target = "image", source = "image") //add mapping
    Product toEntity(ProductDto productDTO);

    @Mapping(target = "userId",source = "user.id")
    CommentDto toDTO(Comment comment);
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product", ignore = true)
    Comment toEntity(CommentDto commentDTO);
}
