package com.example.backoffice.review.service;

import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.review.dto.CreateReviewRequset;
import com.example.backoffice.review.dto.CreateReviewResponse;
import com.example.backoffice.review.entity.Review;
import com.example.backoffice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateReviewResponse save(CreateReviewRequset requset, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new IllegalArgumentException("주문 내역 없음")//에러 코드 추후 수정
        );
        Review review = new Review(
                requset.getRating(),
                requset.getContent(),
                order
        );
        Review savedReview = reviewRepository.save(review);
        return new CreateReviewResponse(
                savedReview.getId(),
                savedReview.getOrder().getOrderNumber(),
                savedReview.getOrder().getUser().getName(),
                savedReview.getRating(),
                savedReview.getContent(),
                savedReview.getCreatedAt(),
                savedReview.getModifiedAt()
        );
    }
}
