package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

/**
 * Curation 생성 요청 DTO
 *
 * @author dino9881
 */

@Builder
public record RequestCreateCurationDto(Long bookId, String title, String content) {

}
