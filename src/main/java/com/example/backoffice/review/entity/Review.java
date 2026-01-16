package com.example.backoffice.review.entity;

import com.example.backoffice.common.config.BaseEntity;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id",nullable = false)
    private Product product;

    public Review(Integer rating, String content, Order order, Product product) {
        this.rating = rating;
        this.content = content;
        this.order = order;
        this.product = product;
    }
}
