package com.example.backoffice.review.service;

import com.example.backoffice.order.entity.Order;
import com.example.backoffice.order.repository.OrderRepository;
import com.example.backoffice.product.entity.Product;
import com.example.backoffice.product.repository.ProductRepository;
import com.example.backoffice.review.dto.CreateReviewRequest;
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
    private final ProductRepository productRepository;

    @Transactional
    public CreateReviewResponse save(CreateReviewRequest requset, Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                ()-> new IllegalArgumentException("주문 내역 없음")//에러 코드 추후 수정
        );
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalStateException("상품이 없습니다.")
        );

        Review review = new Review(
                requset.getRating(),
                requset.getContent(),
                order,
                product
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
