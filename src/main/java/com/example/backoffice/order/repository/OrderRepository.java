package com.example.backoffice.order.repository;

import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.UserOrderDto;
import com.example.backoffice.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

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

    @Query("SELECT NEW com.example.backoffice.order.dto.UserOrderDto(o.user.id, count(o.id), sum(o.amount))" +
            " FROM Order o" +
            " WHERE o.user.id in :userIdList" +
            " AND o.status != :cancelledStatus" +
            " GROUP BY o.user.id")
    List<UserOrderDto> findUserOrderDtoByUserIdList(List<Long> userIdList, OrderStatus cancelledStatus);


    @Query("SELECT NEW com.example.backoffice.order.dto.UserOrderDto(o.user.id, count(o.id), sum(o.amount))" +
            " FROM Order o" +
            " WHERE o.user.id = :userId" +
            " AND o.status != :cancelledStatus" +
            " GROUP BY o.user.id")
    Optional<UserOrderDto> findUserOrderDtoByUserId(Long userId, OrderStatus cancelledStatus);
}

