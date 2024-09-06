package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

/**
 * Curation 상세 조회 응답 DTO
 *
 * @author dino9881
 */
@Builder
public record ResponseCurationDetailDto(
    String coverImgUrl,
    String curationTitle,
    String writer,
    String content,
    String createdAt,
    String bookTitle,
    String author,
    String summary,
    Boolean isStored
) {

}
