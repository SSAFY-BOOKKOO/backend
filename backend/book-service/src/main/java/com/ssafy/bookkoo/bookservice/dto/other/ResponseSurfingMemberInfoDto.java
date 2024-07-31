package com.ssafy.bookkoo.bookservice.dto.other;

import lombok.Builder;

@Builder
public record ResponseSurfingMemberInfoDto(
    String nickName,
    String profilImgUrl
) {

}
