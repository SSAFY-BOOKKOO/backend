package com.ssafy.bookkoo.curationservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/**
 * Curation 생성 요청 DTO
 *
 * @author dino9881
 */

@Builder
public record RequestCreateCurationDto(
    @NotNull(message = "bookId can not be null") Long bookId,
    @Size(min = 2, max = 20, message = "제목의 길이는 2 ~ 20자 입니다.") String title,
    @Size(min = 1, max = 500, message = "내용의 길이는 1~ 500 자 입니다.") String content
) {

}
