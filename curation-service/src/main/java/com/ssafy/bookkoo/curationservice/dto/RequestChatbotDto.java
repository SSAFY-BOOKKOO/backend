package com.ssafy.bookkoo.curationservice.dto;

import com.ssafy.bookkoo.curationservice.entity.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RequestChatbotDto(
    @NotNull(message = "역할이 필요합니다.") Role role,
    @Size(min = 1, max = 200, message = "내용의 길이는 1~ 200 자 입니다.") String content) {
}
