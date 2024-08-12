package com.ssafy.bookkoo.libraryservice.dto.other;

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
    String content,

    @Schema(description = "리뷰 평점", example = "5")
    Integer rating,

    @Schema(description = "좋아요 개수", example = "25")
    Integer likeCount
) {

}
