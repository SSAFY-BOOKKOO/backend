package com.ssafy.bookkoo.bookservice.dto.review;

import lombok.Builder;

@Builder
public record ResponseReviewDto(
    Long id,
    Long bookId,
    Long memberId,
    String content,
    Integer rating
) {

}
