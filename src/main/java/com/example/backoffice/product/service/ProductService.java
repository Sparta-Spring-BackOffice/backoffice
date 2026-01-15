package com.example.backoffice.product.service;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.dto.*;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
    public CreateProductResponse createProduct(Long adminId, CreateProductRequest request, ProductStatus productStatus) {
        Administrator admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND)
        );

        Product product = new Product(
                request.getName(),
                request.getCategory(),
                request.getPrice(),
                request.getStock(),
                ProductStatus.FOR_SALE,
                admin
        );
        Product savedProduct = productRepository.save(product);
        return new CreateProductResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getCategory(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getStatus().getStatusName(),
                savedProduct.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<GetProductResponse> findAllProduct(String keyword, String category, ProductStatus status, Pageable pageable) {
        Page<Product> products = productRepository.findAllProducts(keyword, category, status, pageable);
        return products.map(product ->  new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().getStatusName(),
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getAdministrator().getName()
        ));
    }

    @Transactional(readOnly = true)
    public GetOneProductResponse findOneProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품 입니다.") // 에러 코드 수정 예정
        );
        return new GetOneProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus().getStatusName(),
                product.getCreatedAt(),
                product.getModifiedAt(),
                product.getAdministrator().getName(),
                product.getAdministrator().getEmail()
        );
    }

    @Transactional
    public UpdateProductInfoResponse updateProductInfo(UpdateProductInfoRequest request, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품 입니다.") // 에러 코드 수정 예정
        );

        product.updateInfo(request.getName(), request.getCategory(), request.getPrice());
        return new UpdateProductInfoResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getCreatedAt(),
                product.getModifiedAt()
        );
    }

    @Transactional
    public UpdateProductStockResponse updateProductStock(UpdateProductStockRequest request, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품 입니다.") // 에러 코드 수정 예정
        );

        product.updateStock(request.getStock());
        return new UpdateProductStockResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getStock(),
                product.getStatus().getStatusName(),
                product.getCreatedAt(),
                product.getModifiedAt()
        );
    }
}
