package com.ssafy.bookkoo.memberservice.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ResponseQuoteDetailDto(
    Long quoteId,
    String source,
    String content,
    String fontName,
    String fontColor,
    Integer fontSize,
    String backgroundImgUrl,
    LocalDateTime createdAt
) {

}
