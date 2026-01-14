package com.example.backoffice.product.repository;

import com.example.backoffice.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
    "(:keyword IS NULL OR p.name LIKE %:keyword%) AND " +
    "(:category IS NULL OR p.category = :category) AND " +
    "(:status IS NULL OR p.status = :status)")
    Page<Product> findAllProducts(@Param("keyword") String keyword,
                                  @Param("category") String category,
                                  @Param("status") String status,
                                  Pageable pageable);
}
