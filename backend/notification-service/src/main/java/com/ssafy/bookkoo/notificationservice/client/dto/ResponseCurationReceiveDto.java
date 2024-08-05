package com.ssafy.bookkoo.notificationservice.client.dto;

import lombok.Builder;

/**
 * Curation 상세 조회 응답 DTO
 */
@Builder
public record ResponseCurationReceiveDto(
    String writer, //닉네임
    String writerId
) {

}

