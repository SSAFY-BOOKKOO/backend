package com.ssafy.bookkoo.bookservice.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 요청 DTO")
public record RequestReviewDto(

    @Schema(description = "리뷰 내용", example = "This is a great book!")
    String content
) {

}
