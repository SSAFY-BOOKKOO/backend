package com.ssafy.bookkoo.memberservice.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ResponseMemberProfileDto(
    String memberId,
    String email,
    String nickName,
    String profileImgUrl,
    String introduction,
    List<Integer> categories
) {

}
