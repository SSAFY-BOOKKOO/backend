package com.ssafy.bookkoo.bookservice.dto.review;

import com.ssafy.bookkoo.bookservice.dto.other.ResponseSurfingMemberInfoDto;
import lombok.Builder;

@Builder
public record ResponseSurfingReviewDto(
    Long id,
    Long bookId,
    Long memberId,
    String content,
    Integer rating,
    Integer likeCount, // 좋아요 개수
    ResponseSurfingMemberInfoDto member // 사용자 정보
) {

}
