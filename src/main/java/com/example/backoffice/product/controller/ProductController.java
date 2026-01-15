package com.example.backoffice.product.controller;

import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginFailException;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.dto.*;
import com.example.backoffice.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/admin/products") // Valid 수정 예정
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin, @RequestBody CreateProductRequest request, ProductStatus productStatus){
        if (sessionAdmin == null) {
            throw new LoginFailException(AuthErrorCode.LOGIN_DENIED_ERROR);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(sessionAdmin.getId(), request, productStatus));
    }

    @GetMapping("/admin/products")
    public ResponseEntity<Page<GetProductResponse>> getAllProduct(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) ProductStatus productStatus,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "1") int page

    )
    {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(keyword, category, productStatus, converted));
    }

    @GetMapping("/admin/products/{productId}")
    public ResponseEntity<GetOneProductResponse> getOneProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findOneProduct(productId));
    }

    @PutMapping("/admin/products/{productId}") // Valid 수정 예정
    public ResponseEntity<UpdateProductInfoResponse> updateProduct(@RequestBody UpdateProductInfoRequest request, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductInfo(request, productId));
    }

    @PutMapping("/admin/products/stock/{productId}") // Valid 수정 예정
    public ResponseEntity<UpdateProductStockResponse> updateProductStock(@RequestBody UpdateProductStockRequest request, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductStock(request, productId));
    }

    @PutMapping("/admin/products/status/{productId}")
    public ResponseEntity<UpdateProductStatusResponse> updateProductStatus(@RequestBody UpdateProductStatusRequest request, @PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductStatus(request, productId));
    }
}
