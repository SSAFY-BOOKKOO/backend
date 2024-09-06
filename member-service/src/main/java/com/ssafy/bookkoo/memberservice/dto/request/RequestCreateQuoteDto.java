package com.ssafy.bookkoo.memberservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestCreateQuoteDto(
    @NotBlank(message = "출처는 공백일 수 없습니다.")
    String source,
    @NotBlank(message = "글귀는 공백일 수 없습니다.")
    @Size(max = 200, message = "글귀는 최대 200자 입니다.")
    String content,
    @Schema(example = "Pretendard")
    String fontName,
    @Schema(example = "text-black")
    String fontColor,
    @Schema(example = "2")
    Integer fontSize
) {
    public RequestCreateQuoteDto {
        if (fontName == null) {
            fontName = "Pretendard";
        }
        if (fontColor == null) {
            fontColor = "text-black";
        }
        if (fontSize == null) {
            fontSize = 2;
        }
    }
}
