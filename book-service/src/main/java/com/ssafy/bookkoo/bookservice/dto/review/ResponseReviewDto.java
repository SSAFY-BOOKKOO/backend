package com.ssafy.bookkoo.bookservice.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "리뷰 응답 DTO")
public record ResponseReviewDto(

    @Schema(description = "리뷰 ID", example = "1")
    Long id,

    @Schema(description = "책 ID", example = "101")
    Long bookId,

    @Schema(description = "리뷰 내용", example = "This book provides great insights into modern programming practices.")
    String content

) {

}
