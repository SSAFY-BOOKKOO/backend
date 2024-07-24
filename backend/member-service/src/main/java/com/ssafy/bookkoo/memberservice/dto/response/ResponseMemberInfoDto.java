package com.ssafy.bookkoo.memberservice.dto.response;

import com.ssafy.bookkoo.memberservice.entity.Gender;
import lombok.Builder;

@Builder
public record ResponseMemberInfoDto(
    String nickName,
    Gender gender,
    String[] categories,
    int age,
    String introduction,
    String profilImgUrl
) {

}
