package com.example.backoffice.order.entity;

import com.example.backoffice.common.config.BaseEntity;
import com.example.backoffice.common.exception.ErrorCode;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.exception.AlreadyCompletedException;
import com.example.backoffice.order.exception.UnableCancelException;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;
    private Long quantity;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private String cancelReason;

    public Order(String orderNumber, Long quantity, BigDecimal amount, OrderStatus status, User user, Product product) {
        this.orderNumber = orderNumber;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.user = user;
        this.product = product;
    }

    public void statusNext(){
        this.status = this.status.next();
    }

    public void cancel(String reason){
        if(this.status != OrderStatus.READY){
            throw new UnableCancelException(ErrorCode.UNABLE_CANCEL);
        }
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = reason;
    }
}
