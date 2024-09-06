package com.ssafy.bookkoo.bookservice.dto.other;

import com.ssafy.bookkoo.bookservice.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
@Schema(description = "회원 정보 응답 DTO")
public record ResponseMemberInfoDto(

    @Schema(description = "회원 닉네임", example = "johnDoe")
    String nickName,

    @Schema(description = "회원 성별", example = "MALE")
    Gender gender,

    @Schema(description = "회원 관심 카테고리 ID 리스트", example = "[1, 2, 3]")
    List<Integer> categories,

    @Schema(description = "회원 나이", example = "30")
    int age,

    @Schema(description = "회원 자기소개", example = "Hello, I am John Doe.")
    String introduction,

    @Schema(description = "회원 프로필 이미지 URL", example = "http://example.com/profile.jpg")
    String profileImgUrl
) {

}
