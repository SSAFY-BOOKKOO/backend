package com.ssafy.bookkoo.memberservice.dto.request;

import com.ssafy.bookkoo.memberservice.annotation.MaxArray;
import com.ssafy.bookkoo.memberservice.enums.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record RequestAdditionalInfo(
    @NotBlank(message = "ID는 공백일 수 없습니다.")
    Long id,
    @NotBlank(message = "멤버 ID는 공백일 수 없습니다.")
    String memberId,
    @Size(max = 10, message = "닉네임은 최대 10자 입니다.")
    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    String nickName,
    Integer year,
    Gender gender,
    @MaxArray(minValue = 1, maxValue = 15, message = "카테고리에 대한 값이 유효하지 않습니다.")
    Integer[] categories,
    @Size(max = 200, message = "소개글은 최대 200자 입니다.")
    String introduction,
    String profileImgUrl) {

}
