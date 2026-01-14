package com.example.backoffice.product.service;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.dto.GetOneProductResponse;
import com.example.backoffice.product.dto.GetProductResponse;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    @Transactional
    public CreateProductResponse createProduct(CreateProductRequest request, Long adminId) {
        Administrator admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException()
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
    public Page<GetProductResponse> findAllProduct(String keyword, String category, String status, Pageable pageable) {
        Page<Product> products = productRepository.findAllProducts(keyword, category, status, pageable);
        return products.map(product ->  new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getAdministrator().getName()
        ));
    }

    public GetOneProductResponse findOneProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품 입니다.")
        );
        return new GetOneProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getAdministrator().getName(),
                product.getAdministrator().getEmail()
        );
    }
}
