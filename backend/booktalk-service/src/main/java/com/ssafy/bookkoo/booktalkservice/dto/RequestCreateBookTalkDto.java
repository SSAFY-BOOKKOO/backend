package com.ssafy.bookkoo.booktalkservice.dto;

import lombok.Builder;

@Builder
public record RequestCreateBookTalkDto(
    Long bookId
) {

}

