package com.example.backoffice.product.controller;

import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.dto.GetProductResponse;
import com.example.backoffice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/products")
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request, Long adminId){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request, adminId));
    }

    @GetMapping("/admin/products")
    public ResponseEntity<Page<GetProductResponse>> getAllProduct(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(page, size));
    }
}
