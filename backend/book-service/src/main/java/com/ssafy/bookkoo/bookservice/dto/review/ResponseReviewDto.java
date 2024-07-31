package com.ssafy.bookkoo.bookservice.dto.review;

import lombok.Builder;

@Builder
public record ResponseReviewDto(
    Long id,
    Long bookId,
    Long memberId,
    String content,
    Integer rating,
    Integer likeCount // 좋아요 개수
) {

}
