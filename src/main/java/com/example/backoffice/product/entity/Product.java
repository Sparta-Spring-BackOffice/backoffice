package com.example.backoffice.product.entity;

import com.example.backoffice.admin.entity.Administrator;
import com.example.backoffice.common.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Long stock;
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_id", nullable = false)
    private Administrator administrator;


    public Product(String name, String category, BigDecimal price, Long stock, String status, Administrator administrator) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.administrator = administrator;
    }

    public void updateInfo(String name, String category, BigDecimal price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void updateStock(Long stock) {
        this.stock = stock;
    }
}
