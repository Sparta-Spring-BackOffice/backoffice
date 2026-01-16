package com.example.backoffice.order.controller;

import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.GetOrderResponse;
import com.example.backoffice.order.service.OrderService;
import com.example.backoffice.user.consts.UserStatus;
import com.example.backoffice.user.dto.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/admin/orders")
    public ResponseEntity<Page<GetOrderResponse>> getOrders(
            @RequestParam(required = false) String keyword,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) OrderStatus status
    ) {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );

        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAllOrders(keyword, converted, status));
    }
}
