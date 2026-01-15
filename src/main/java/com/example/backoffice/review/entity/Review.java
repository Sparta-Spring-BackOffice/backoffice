package com.example.backoffice.review.entity;

import com.example.backoffice.common.config.BaseEntity;
import com.example.backoffice.order.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1, max = 5)
    private Integer rating;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Review(Integer rating, String content, Order order) {
        this.rating = rating;
        this.content = content;
        this.order = order;
    }
}
