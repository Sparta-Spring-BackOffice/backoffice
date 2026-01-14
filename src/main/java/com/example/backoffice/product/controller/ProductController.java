package com.example.backoffice.product.controller;

import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request));
    }
}
