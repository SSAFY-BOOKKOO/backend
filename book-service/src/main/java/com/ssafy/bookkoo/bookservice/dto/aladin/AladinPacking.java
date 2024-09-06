package com.ssafy.bookkoo.bookservice.dto.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 알라딘 API로부터 받아오는 책 포장 정보를 담는 DTO 클래스입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "알라딘 API로부터 받아오는 책 포장 정보를 담는 DTO")
public class AladinPacking {

    @Schema(description = "포장 깊이", example = "10")
    private Integer sizeDepth;

    @Schema(description = "포장 높이", example = "20")
    private Integer sizeHeight;

    @Schema(description = "포장 너비", example = "15")
    private Integer sizeWidth;
}
