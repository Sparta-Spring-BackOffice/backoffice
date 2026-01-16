package com.example.backoffice.review.service;

import com.example.backoffice.review.dto.GetReviewResponse;
import com.example.backoffice.review.entity.Review;
import com.example.backoffice.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<GetReviewResponse> findReview(String keyword, Pageable pageable, Integer rating){
        String searchKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword;
        if (rating != null &&(rating < 1 || rating > 5)) {
            throw new IllegalStateException("평점은 1~5만 가능합니다."); // 에러 코드 수정 예정
        }
        Page<Review> reviews = reviewRepository.findByKeywordAndRating(searchKeyword, rating, pageable);

        return reviews.map(review -> new GetReviewResponse(
                review.getId(),
                review.getOrder().getOrderNumber(),
                review.getOrder().getUser().getName(),
                review.getProduct().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getModifiedAt()
        ));
    }
}
