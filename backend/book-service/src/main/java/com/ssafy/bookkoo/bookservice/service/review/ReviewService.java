package com.ssafy.bookkoo.bookservice.service.review;

import com.ssafy.bookkoo.bookservice.dto.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseReviewDto;
import java.util.List;

public interface ReviewService {

    List<ResponseReviewDto> getReviewByBookId(Long bookId);

    ResponseReviewDto getReviewById(Long bookId, Long reviewId);

    ResponseReviewDto addReview(Long memberId, Long bookId, RequestReviewDto requestReviewDto);

    Boolean likeReview(Long memberId, Long bookId, Long reviewId);
}
