package com.ssafy.bookkoo.memberservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * Member 생성 요청 DTO
 * email : 이메일 형식 확인
 * password : 비밀번호 형식 (8~12자 영문, 숫자, 특수기호 포함)
 * nickName : 닉네임 필수
 * year : 출생년도
 * categories : 선호 카테고리들
 */
@Builder
public record RequestRegisterDto(
    @Pattern(
        regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "Invalid email address"
    ) String email,
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
        message = "Invalid password."
    ) String password) {

}
