package com.ssafy.bookkoo.bookservice.dto.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 알라딘 API로부터 받아오는 응답 정보를 담는 DTO 클래스입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "알라딘 API로부터 받아오는 응답 정보를 담는 DTO")
public class ResponseAladinAPI {

    @Schema(description = "총 결과 수", example = "100")
    private Integer totalResults;

    @Schema(description = "시작 인덱스", example = "1")
    private Integer startIndex;

    @Schema(description = "책 아이템 리스트")
    private List<AladinBookItem> item;

    @Schema(description = "페이지당 아이템 수", example = "10")
    private Integer itemsPerPage;
}
