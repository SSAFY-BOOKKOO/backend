package com.ssafy.bookkoo.memberservice.dto.response;


import lombok.Builder;

@Builder
public record ResponseRecipientDto(
    Long memberId
) {

}
