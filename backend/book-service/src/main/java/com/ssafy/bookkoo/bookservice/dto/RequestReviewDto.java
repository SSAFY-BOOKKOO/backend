package com.ssafy.bookkoo.bookservice.dto;

import lombok.Builder;

@Builder
public record RequestReviewDto(
    String content,
    Integer rating
) {

}
