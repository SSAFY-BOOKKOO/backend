package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import com.ssafy.bookkoo.memberservice.enums.Gender;
import com.ssafy.bookkoo.memberservice.enums.SocialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestRegisterMemberDto(
    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "이메일 형식이 올바르지 않습니다."
    ) String email,
    String password,
    @Size(max = 10, message = "닉네임은 최대 10자 입니다.")
    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    String nickName,
    Integer year,
    Gender gender,
    @MaxArray(minValue = 1, maxValue = 15, message = "카테고리에 대한 값이 유효하지 않습니다.")
    Integer[] categories,
    @Size(max = 200, message = "소개글은 최대 200자 입니다.")
    String introduction,
    SocialType socialType,
    String profileImgUrl,
    RequestMemberSettingDto memberSettingDto
) {

    /**
     * Default SocialType: bookkoo Gender: NONE
     */
    public RequestRegisterMemberDto {
        if (socialType == null) {
            socialType = SocialType.bookkoo;
        }
        if (gender == null) {
            gender = Gender.NONE;
        }
    }
}
