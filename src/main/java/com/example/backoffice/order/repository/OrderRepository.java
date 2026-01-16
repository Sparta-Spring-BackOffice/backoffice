package com.example.backoffice.order.repository;

import com.example.backoffice.order.dto.UserOrderDto;
import com.example.backoffice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT NEW com.example.backoffice.order.dto.UserOrderDto(o.user.id, count(o.user.id), sum() )" +
            " FROM Order o JOIN o.user u" +
            " WHERE o.user.id in :userIdList" +
            " GROUP BY o.user.id")
    List<UserOrderDto> findUserOrderDtoByUserID(List<Long> userIdList);
}

