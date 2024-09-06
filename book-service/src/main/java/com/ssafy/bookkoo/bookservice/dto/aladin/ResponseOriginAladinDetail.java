package com.ssafy.bookkoo.bookservice.dto.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 알라딘 API로부터 받아오는 원본 상세 응답 정보를 담는 DTO 클래스입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "알라딘 API로부터 받아오는 원본 상세 응답 정보를 담는 DTO")
public class ResponseOriginAladinDetail {

    @Schema(description = "책 상세 검색 결과 아이템 리스트")
    private List<ResponseOriginAladinSearchDetail> item;
}
