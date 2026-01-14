package com.example.backoffice.product.service;

import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public CreateProductResponse createProduct(CreateProductRequest request) {
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus()
        );
        Product savedProduct = productRepository.save(product);
        return new CreateProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getCategory(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getStatus(),
                savedProduct.getCreatedAt()
        );


    }
}
