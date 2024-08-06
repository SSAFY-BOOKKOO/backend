package com.ssafy.bookkoo.booktalkservice.dto;

import lombok.Builder;

@Builder
public record ResponseBookTalkDto(
    Long bookTalkId,
    String title,
    String author,
    String[] categories,
    String coverImgUrl,
    String createdAt,
    Long comments
) {

}
