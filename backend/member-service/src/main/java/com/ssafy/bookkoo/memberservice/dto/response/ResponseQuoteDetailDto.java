package com.ssafy.bookkoo.memberservice.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResponseQuoteDetailDto(
    String source,
    String content,
    String backgroundImgUrl,
    LocalDateTime createdAt
) {

}
