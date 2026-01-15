package com.example.backoffice.review.controller;

import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.order.dto.CreateOrderResponse;
import com.example.backoffice.review.dto.CreateReviewRequest;
import com.example.backoffice.review.dto.CreateReviewResponse;
import com.example.backoffice.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/orders/{orderId}/products/{productId}/reviews")//리뷰는 어드민에서 사용하는 곳이 아니라서 admin 사용안함
    public ResponseEntity<CreateReviewResponse> create(
            @PathVariable Long orderId, Long productId,
           @Valid @RequestBody CreateReviewRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.save(request,orderId, productId));
    }
}
