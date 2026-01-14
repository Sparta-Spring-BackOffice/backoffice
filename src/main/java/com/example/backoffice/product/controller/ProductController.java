package com.example.backoffice.product.controller;

import com.example.backoffice.admin.exception.AdminNotFoundException;
import com.example.backoffice.admin.service.AdminService;
import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.product.dto.CreateProductRequest;
import com.example.backoffice.product.dto.CreateProductResponse;
import com.example.backoffice.product.dto.GetOneProductResponse;
import com.example.backoffice.product.dto.GetProductResponse;
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
    private final AdminService adminService;

    @PostMapping("/admin/products")
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin, @RequestBody CreateProductRequest request){
        if (sessionAdmin == null) {
            throw new AdminNotFoundException(ErrorCode.ADMIN_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(sessionAdmin.getId(), request));
    }

    @GetMapping("/admin/products")
    public ResponseEntity<Page<GetProductResponse>> getAllProduct(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "1") int page
    )
    {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProduct(keyword, category, status, converted));
    }

    @GetMapping("/admin/products/{productId}")
    public ResponseEntity<GetOneProductResponse> getOneProduct(@PathVariable Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findOneProduct(productId));
    }
}
