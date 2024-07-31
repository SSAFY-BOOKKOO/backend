package com.ssafy.bookkoo.bookservice.service.review;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import java.util.List;

public interface ReviewService {

    List<ResponseReviewDto> getReviewByBookId(Long bookId);

    ResponseReviewDto getReviewById(Long bookId, Long reviewId);

    ResponseReviewDto addReview(Long memberId, Long bookId, RequestReviewDto requestReviewDto);

    Boolean toggleLikeReview(Long memberId, Long bookId, Long reviewId);
}
