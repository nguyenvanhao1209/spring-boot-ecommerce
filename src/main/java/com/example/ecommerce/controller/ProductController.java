package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.ProductListDto;
import com.example.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product", description = "Create a new product")
    public ResponseEntity<ProductDto> createProduct(@RequestPart("product") @Valid ProductDto productDTO,
                                                    @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.createProduct(productDTO, image));
    }

    @PutMapping(value = "/{id}/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "Update product by id")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id,
                                                    @RequestPart("product") @Valid ProductDto productDTO,
                                                    @RequestPart(value = "image", required = false) MultipartFile image)throws IOException{
        return ResponseEntity.ok(productService.updateProduct(id, productDTO, image));
    }

    @DeleteMapping("/{id}/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product", description = "Delete product by id")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/")
    @Operation(summary = "Get product by id", description = "Get product by id")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProduct(id));
    }
    @GetMapping("/")
    @Operation(summary = "Get all products", description = "Get all products")
    public ResponseEntity<List<ProductListDto>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
