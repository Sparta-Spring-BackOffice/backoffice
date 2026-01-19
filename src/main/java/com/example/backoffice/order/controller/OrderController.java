package com.example.backoffice.order.controller;

import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.LoginFailException;
import com.example.backoffice.common.dto.SuccessResponse;
import com.example.backoffice.common.responsecode.ResponseProcess;
import com.example.backoffice.common.responsecode.SuccessCode;
import com.example.backoffice.order.consts.OrderStatus;
import com.example.backoffice.order.dto.*;
import com.example.backoffice.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/admin/orders")
    public ResponseEntity<SuccessResponse<CreateOrderResponse>> createOrder (@Valid @RequestBody CreateOrderRequest request, Authentication auth) {

        Long adminId = (Long) auth.getPrincipal();
        return ResponseProcess.responseWithBody(SuccessCode.CREATE_SUCCESS, orderService.createOrder(request, adminId));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<SuccessResponse<Page<GetOrderResponse>>> getOrders(
            @RequestParam(required = false) String keyword,
            @PageableDefault Pageable pageable,
            @RequestParam(defaultValue = "1") @Min(1) int page,
            @RequestParam(required = false) OrderStatus status
    ) {
        Pageable converted = PageRequest.of(
                page - 1,
                pageable.getPageSize(),
                pageable.getSort()
        );
        return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, orderService.findAllOrders(keyword, converted, status));
    }

    @GetMapping("/admin/orders/{orderId}")
    public ResponseEntity<SuccessResponse<GetOneOrderResponse>> getOneOrder(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin sessionAdmin
    ) {
        boolean isAdmin = (sessionAdmin != null);
        return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, orderService.findOneOrder(orderId, isAdmin));
    }

    @PutMapping("/admin/orders/{orderId}")
    public ResponseEntity<SuccessResponse<ChangedOrderStatusResponse>> changedStatusOrder(
            @PathVariable Long orderId
    ) {
        return ResponseProcess.responseWithBody(SuccessCode.UPDATE_SUCCESS, orderService.changedStatusOrder(orderId));
    }

    @PutMapping("/admin/orders/{orderId}/cancelled")
    public ResponseEntity<SuccessResponse<Void>>  cancelledOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody CancelledOrderRequest request
    ) {
        orderService.cancelledOrder(orderId,request);
        return ResponseProcess.responseWithBuildNewMessage(SuccessCode.UPDATE_SUCCESS,null,"주문이 취소 되었습니다.");
    }
}
