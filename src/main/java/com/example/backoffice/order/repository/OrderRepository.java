package com.example.backoffice.order.repository;

import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o FROM Order o " +
            "WHERE (:keyword IS NULL OR o.orderNumber LIKE %:keyword%) " +
            "AND (:status IS NULL OR o.status = :status)")
    Page<Order> findByOrderNumberKeyword(String keyword, Pageable pageable, OrderStatus status);

    @Query(value = "SELECT o FROM Order o " +
            "JOIN o.user u " +
            "WHERE (:keyword IS NULL OR u.name LIKE %:keyword%) " +
            "AND (:status IS NULL OR o.status = :status)")
    Page<Order> findByNameKeyword(String keyword, Pageable pageable, OrderStatus status);
}
