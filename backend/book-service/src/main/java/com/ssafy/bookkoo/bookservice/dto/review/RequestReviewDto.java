package com.ssafy.bookkoo.bookservice.dto.review;

import lombok.Builder;

@Builder
public record RequestReviewDto(
    String content,
    Integer rating
) {

}
