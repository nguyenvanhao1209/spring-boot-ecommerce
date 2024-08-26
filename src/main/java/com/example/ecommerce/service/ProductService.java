package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.ProductListDto;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public ProductDto createProduct(ProductDto productDTO, MultipartFile image) throws IOException;

    public ProductDto updateProduct(Long id, ProductDto productDTO, MultipartFile image) throws IOException;

    public void deleteProduct(Long id);

    public ProductDto getProduct(Long id);

    public List<ProductListDto> getAllProducts();
}
