package com.ssafy.bookkoo.booktalkservice.dto;

import lombok.Builder;

@Builder
public record RequestChatMessageDto(
    String content
) {

}
