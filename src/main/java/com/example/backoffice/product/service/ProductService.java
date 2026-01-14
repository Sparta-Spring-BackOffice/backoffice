package com.example.backoffice.product.service;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.dto.GetProductResponse;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request, Long adminId) {
        Administrator admin = adminRepository.findById(adminId).orElseThrow(
                () -> new IllegalArgumentException("없는 관리자 입니다.")
        );
        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                request.getStatus(),
                admin
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

    @Transactional(readOnly = true)
    public Page<GetProductResponse> findAllProduct(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findAllByName(
                pageable, Sort.by(
                        Sort.Order.asc("price"),
                        Sort.Order.asc("stock"),
                        Sort.Order.asc("createdAt"),
                        Sort.Order.desc("price"),
                        Sort.Order.desc("stock"),
                        Sort.Order.desc("createdAt")
                ));

        return products.map(
                product -> new GetProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getStock(),
                        product.getStatus(),
                        product.getCreatedAt(),
                        product.getAdministrator()
                ));
    }
}
