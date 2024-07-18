package com.ssafy.bookkoo.curationservice.dto;

import lombok.Builder;

/**
 * Curation 목록 조회 응답 DTO
 *
 * @author dino9881
 */

@Builder
public record ResponseCurationDto(String writer, String title, String coverImgUrl) {

}
