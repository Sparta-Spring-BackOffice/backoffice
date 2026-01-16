package com.example.backoffice.review.controller;

import com.example.backoffice.authentification.dto.SessionAdmin;
import com.example.backoffice.authentification.exception.AuthErrorCode;
import com.example.backoffice.authentification.exception.UnauthorizedException;
import com.example.backoffice.common.dto.SuccessResponse;
import com.example.backoffice.common.responsecode.ResponseProcess;
import com.example.backoffice.common.responsecode.SuccessCode;
import com.example.backoffice.product.service.ProductService;
import com.example.backoffice.review.dto.GetOneReviewResponse;
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
    private final ProductService productService;

    @GetMapping("/admin/reviews")
   public ResponseEntity<SuccessResponse<Page<GetReviewResponse>>> getReviews(
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

       return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, reviewService.findReview(keyword, converted, rating));
   }

   @GetMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<SuccessResponse<GetOneReviewResponse>> getReview(@PathVariable Long  reviewId) {
       return ResponseProcess.responseWithBody(SuccessCode.READ_SUCCESS, reviewService.findOne(reviewId));
   }

   @DeleteMapping("/admin/reviews/{reviewId}")
    public ResponseEntity<SuccessResponse<Void>> deleteReview(
            @PathVariable Long  reviewId,
            @SessionAttribute(name = "loginUser", required = false) SessionAdmin loginAdmin
   ) {
       if(loginAdmin == null){
           throw new UnauthorizedException(AuthErrorCode.NOT_LOGIN);
       }
       reviewService.deleteReview(reviewId);
       return ResponseProcess.responseWithBuild(SuccessCode.DELETE_SUCCESS, null);
   }
}
