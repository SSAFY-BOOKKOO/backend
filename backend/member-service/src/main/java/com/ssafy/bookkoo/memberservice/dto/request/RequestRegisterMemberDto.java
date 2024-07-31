package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import com.ssafy.bookkoo.memberservice.entity.Gender;
import com.ssafy.bookkoo.memberservice.entity.SocialType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RequestRegisterMemberDto (
    @NotNull
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Invalid email address"
    ) String email,
    String password,
    @NotNull(message = "nickName can not be null") String nickName,
    Integer year,
    Gender gender,
    @MaxArray(value = 15) Integer[] categories,
    String introduction,
    SocialType socialType,
    String profileImgUrl
) {

    /**
     * SocialType이 없으면 bookkoo로 설정
     */
    public RequestRegisterMemberDto {
        if (socialType == null) {
            socialType = SocialType.bookkoo;
        }
    }
}
