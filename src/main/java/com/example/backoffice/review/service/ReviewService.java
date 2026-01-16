package com.example.backoffice.review.service;

import com.example.backoffice.admin.repository.AdminRepository;
import com.example.backoffice.common.responsecode.ErrorCode;
import com.example.backoffice.review.dto.GetOneReviewResponse;
import com.example.backoffice.review.dto.GetReviewResponse;
import com.example.backoffice.review.entity.Review;
import com.example.backoffice.review.exception.InvalidRatingException;
import com.example.backoffice.review.exception.ReviewNotFoundException;
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
    private final AdminRepository adminRepository;

    @Transactional(readOnly = true)
    public Page<GetReviewResponse> findReview(String keyword, Pageable pageable, Integer rating){
        String searchKeyword = (keyword == null || keyword.isEmpty()) ? null : keyword;
        if (rating != null &&(rating < 1 || rating > 5)) {
            throw new InvalidRatingException(ErrorCode.INVALID_RATING);
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

    @Transactional(readOnly = true)
    public GetOneReviewResponse findOne(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND)
        );
        return new GetOneReviewResponse(
                review.getId(),
                review.getProduct().getName(),
                review.getOrder().getUser().getName(),
                review.getOrder().getUser().getEmail(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getModifiedAt()
        );
    }

    @Transactional
    public void deleteReview(Long reviewId){
        boolean existence = reviewRepository.existsById(reviewId);
        if (!existence) {
            throw new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
    }
}
