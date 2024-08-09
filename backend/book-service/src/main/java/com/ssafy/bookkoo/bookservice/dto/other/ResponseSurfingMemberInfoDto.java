package com.ssafy.bookkoo.bookservice.dto.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "서핑 회원 정보 응답 DTO")
public record ResponseSurfingMemberInfoDto(

    @Schema(description = "회원 닉네임", example = "johnDoe")
    String nickName,

    @Schema(description = "회원 프로필 이미지 URL", example = "http://example.com/profile.jpg")
    String profileImgUrl
) {

}
