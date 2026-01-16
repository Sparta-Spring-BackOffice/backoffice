package com.example.backoffice.review.controller;

import com.example.backoffice.review.dto.GetReviewResponse;
import com.example.backoffice.review.service.ReviewService;
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
public class ReviewController {
    private final ReviewService reviewService;

   @GetMapping("/admin/reviews")
   public ResponseEntity<Page<GetReviewResponse>> getReviews(
           @RequestParam(required = false) String keyword,
           @PageableDefault Pageable pageable,
           @RequestParam(defaultValue = "1") int page,
           @RequestParam(required = false) Integer rating
   ){
       Pageable converted = PageRequest.of(
               page -1,
               pageable.getPageSize(),
               pageable.getSort()
       );

       return ResponseEntity.status(HttpStatus.OK).body(reviewService.findReview(keyword, converted, rating));
   }
}
