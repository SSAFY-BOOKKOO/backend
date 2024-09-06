package com.ssafy.bookkoo.bookservice.dto.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 알라딘 API로부터 받아오는 서브 정보를 담는 DTO 클래스입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "알라딘 API로부터 받아오는 서브 정보를 담는 DTO")
public class AladinSubInfo {

    @Schema(description = "아이템 페이지 수", example = "350")
    private Integer itemPage;

    @Schema(description = "포장 정보")
    private AladinPacking packing;
}
