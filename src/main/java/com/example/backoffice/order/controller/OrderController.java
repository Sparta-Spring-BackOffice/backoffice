package com.example.backoffice.order.controller;

import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginFailException;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.*;
import com.example.backoffice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/admin/orders")
    public ResponseEntity<CreateOrderResponse> createOrder (@Valid @RequestBody CreateOrderRequest request, @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin) {
        if (sessionAdmin == null) {
            throw new LoginFailException(AuthErrorCode.NOT_LOGIN);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, sessionAdmin.getId()));
    }

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

    @GetMapping("/admin/orders/{orderId}")
    public ResponseEntity<GetOneOrderResponse> getOneOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin
    ) {
        boolean isAdmin = (sessionAdmin != null);
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findOneOrder(orderId, isAdmin));
    }

    @PutMapping("/admin/orders/{orderId}")
    public ResponseEntity<ChangedStatusOrderResponse> changedStatusOrder(
            @PathVariable Long orderId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.changedStatusOrder(orderId));
    }
}
