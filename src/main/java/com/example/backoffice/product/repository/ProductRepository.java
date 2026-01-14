package com.example.backoffice.product.repository;

import com.example.backoffice.product.dto.GetProductResponse;
import com.example.backoffice.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByName(Pageable pageable, Sort by);
}
