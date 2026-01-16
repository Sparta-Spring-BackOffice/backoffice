package com.example.backoffice.order.service;

import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.GetOrderResponse;
import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public Page<GetOrderResponse> findAllOrders(String keyword, Pageable pageable, OrderStatus status) {

        String searchKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword;

        Page<Order> orders;

        if (searchKeyword != null && searchKeyword.chars().anyMatch(Character::isDigit)) {
            orders = orderRepository.findByOrderNumberKeyword(searchKeyword,pageable,status);
        } else {
            orders = orderRepository.findByNameKeyword(searchKeyword,pageable,status);
        }

        return orders.map(order -> new GetOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getUser().getName(),
                order.getProduct().getName(),
                order.getProduct().getPrice(),
                order.getQuantity(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getModifiedAt(),
                order.getStatus().getStatusName(),
                order.getProduct().getAdministrator().getName()
        ));

    }
}
