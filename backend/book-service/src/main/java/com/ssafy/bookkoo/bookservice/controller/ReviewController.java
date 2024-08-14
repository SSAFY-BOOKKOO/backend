package com.ssafy.bookkoo.bookservice.controller;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseSurfingReviewDto;
import com.ssafy.bookkoo.bookservice.service.review.ReviewService;
import com.ssafy.bookkoo.bookservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "책 리뷰 조회", description = "특정 책의 모든 리뷰를 조회합니다.")
    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ResponseReviewDto>> getReviewsByBookId(
        @PathVariable Long bookId
    ) {
        log.info("Retrieve Review by Book Id: {}", bookId);
        List<ResponseReviewDto> reviews = reviewService.getReviewByBookId(bookId);
        return ResponseEntity.ok(reviews);
    }

    @Operation(summary = "내 리뷰 조회", description = "책 ID와 리뷰 ID를 통해 내 리뷰를 조회합니다.")
    @GetMapping("/{bookId}/review/me")
    public ResponseEntity<ResponseReviewDto> getMyReview(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        ResponseReviewDto review = reviewService.getMyReview(bookId, memberId);
        return ResponseEntity.ok(review);
    }

    @Operation(summary = "특정 리뷰 조회", description = "책 ID와 리뷰 ID를 통해 특정 리뷰를 조회합니다.")
    @GetMapping("/{bookId}/reviews/{reviewId}")
    public ResponseEntity<ResponseReviewDto> getReviewById(
        @PathVariable Long bookId,
        @PathVariable Long reviewId
    ) {
        ResponseReviewDto review = reviewService.getReviewById(bookId, reviewId);
        return ResponseEntity.ok(review);
    }


    @Operation(summary = "리뷰 작성", description = "특정 책에 대한 리뷰를 작성합니다.")
    @PostMapping("/{bookId}/reviews")
    public ResponseEntity<ResponseReviewDto> createReview(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId,
        @Valid @RequestBody RequestReviewDto requestReviewDto
    ) {
        log.info("Create Review: {}", requestReviewDto);
        Long memberId = CommonUtil.getMemberId(headers);
        ResponseReviewDto createdReview = reviewService.addReview(memberId, bookId,
            requestReviewDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdReview);
    }

    @PutMapping("/{bookId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "특정 책에 대한 리뷰를 수정합니다.")
    public ResponseEntity<ResponseReviewDto> updateReview(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId,
        @PathVariable Long reviewId,
        @RequestBody RequestReviewDto dto
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        ResponseReviewDto updatedReview = reviewService.updateReview(memberId, bookId, reviewId,
            dto);
        return ResponseEntity.ok()
                             .body(updatedReview);
    }

    @DeleteMapping("/{bookId}/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "특정 책에 대한 리뷰를 삭제합니다.")
    public ResponseEntity<HttpStatus> deleteReview(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId,
        @PathVariable Long reviewId
    ) {
        log.info("Delete Review: {}", reviewId);
        Long memberId = CommonUtil.getMemberId(headers);
        reviewService.deleteReviewById(memberId, bookId, reviewId);
        return ResponseEntity.noContent()
                             .build();
    }


    @Operation(summary = "리뷰 파도타기 조회", description = "특정 책의 리뷰 중 자신의 리뷰를 제외한 랜덤 리뷰를 조회합니다.")
    @GetMapping("/{bookId}/reviews/surfing")
    public ResponseEntity<List<ResponseSurfingReviewDto>> getSurfingReviewsByBookId(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long bookId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok()
                             .body(reviewService.getRandomReviewExceptMine(memberId, bookId));
    }

    @DeleteMapping("/reviews/me")
    @Operation(
        summary = "사용자 탈퇴시 사용자의 한줄평 삭제",
        description = """
            ### 사용자 탈퇴시 사용할 API
            - 한줄평, 한줄평 좋아요 정보 사라짐

            ### status code : 204(No Content)

            """
    )
    public ResponseEntity<HttpStatus> deleteReviewOfMember(
        @RequestParam("memberId") Long memberId
    ) {
        reviewService.deleteReviewsByMemberId(memberId);

        return ResponseEntity.noContent()
                             .build();
    }

}
