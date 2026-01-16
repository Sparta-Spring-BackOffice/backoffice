package com.example.backoffice.review.repository;

import com.example.backoffice.dashboard.dto.ReviewRatingDto;
import com.example.backoffice.review.dto.ProductReviewStatsDto;
import com.example.backoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
SELECT r FROM Review r
WHERE (:keyword IS NULL OR :keyword = ''
       OR r.order.user.name LIKE CONCAT('%', :keyword, '%')
       OR r.product.name LIKE CONCAT('%', :keyword, '%'))
  AND (:rating IS NULL OR r.rating = :rating)
""")
    Page<Review> findByKeywordAndRating(@Param("keyword") String keyword,
                                        @Param("rating") Integer rating,
                                        Pageable pageable);

    @Query("SELECT NEW com.example.backoffice.review.dto.ProductReviewStatsDto(r.product.id, count(r.id),avg(r.rating), sum(case when r.rating = 1 then 1 else 0 end), sum(case when r.rating = 2 then 1 else 0 end), sum(case when r.rating = 3 then 1 else 0 end), sum(case when r.rating = 4 then 1 else 0 end), sum(case when r.rating = 5 then 1 else 0 end))" +
            " FROM Review r" +
            " where r.product.id = :productId"+
            " GROUP BY r.product.id")
    ProductReviewStatsDto getProductReviewStats(Long productId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r")
    double findAverageRating();

    @Query("SELECT new com.example.backoffice.dashboard.dto.ReviewRatingDto(r.rating, COUNT(r)) "+
           "FROM Review r GROUP BY r.rating")
    List<ReviewRatingDto> countByReviewByRating();
}
