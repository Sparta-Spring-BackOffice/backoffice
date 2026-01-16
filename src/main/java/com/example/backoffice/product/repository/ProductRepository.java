package com.example.backoffice.product.repository;

import com.example.backoffice.dashboard.dto.CategoryDto;
import com.example.backoffice.product.consts.ProductStatus;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.review.dto.ProductReviewStatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
    "(:keyword IS NULL OR p.name LIKE %:keyword%) AND " +
    "(:category IS NULL OR p.category = :category) AND " +
    "(:status IS NULL OR p.status = :status)")
    Page<Product> findAllProducts(@Param("keyword") String keyword,
                                  @Param("category") String category,
                                  @Param("status") ProductStatus status,
                                  Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock < 6")
    Long countByLowStock();

    @Query("SELECT COUNT(p) FROM Product p WHERE p.status = :status")
    Long countBySoldOutStatus(ProductStatus status);

    @Query("SELECT new com.example.backoffice.dashboard.dto.CategoryDto(p.category, count(p)) " +
           "FROM Product p GROUP BY p.category")
    List<CategoryDto> countByCategoryByName();
}
