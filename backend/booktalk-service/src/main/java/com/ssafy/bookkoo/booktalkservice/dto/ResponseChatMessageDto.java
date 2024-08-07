package com.ssafy.bookkoo.booktalkservice.dto;

import lombok.Builder;

@Builder
public record ResponseChatMessageDto(
    String messageId,
    Long bookTalkId,
    String nickName,
    String profileImgUrl,
    String message,
    Long like,
    String createdAt
) {

}
