package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.service.review.ReviewService;
import com.ssafy.bookkoo.bookservice.util.CommonUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ResponseReviewDto>> getReviewsByBookId(@PathVariable Long bookId) {
        List<ResponseReviewDto> reviews = reviewService.getReviewByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{bookId}/reviews/{reviewId}")
    public ResponseEntity<ResponseReviewDto> getReviewById(
        @PathVariable Long bookId,
        @PathVariable Long reviewId
    ) {
        ResponseReviewDto review = reviewService.getReviewById(bookId, reviewId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/{bookId}/reviews")
    public ResponseEntity<ResponseReviewDto> createReview(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId,
        @RequestBody RequestReviewDto requestReviewDto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        ResponseReviewDto createdReview = reviewService.addReview(memberId, bookId,
            requestReviewDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdReview);
    }

    @PostMapping("/{bookId}/reviews/{reviewId}/like")
    public ResponseEntity<Boolean> toggleLikeReviewById(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId,
        @PathVariable Long reviewId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(reviewService.toggleLikeReview(memberId, bookId, reviewId));
    }
}
