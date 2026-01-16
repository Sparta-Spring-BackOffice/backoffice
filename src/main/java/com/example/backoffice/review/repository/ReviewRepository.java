package com.example.backoffice.review.repository;

import com.example.backoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

}
