package com.ssafy.bookkoo.booktalkservice.dto;

import lombok.Builder;

@Builder
public record ResponseBookTalkDto(
    Long bookTalkId,
    String title
) {

}
