package com.ssafy.bookkoo.booktalkservice.dto;

import com.ssafy.bookkoo.booktalkservice.entity.Gender;
import lombok.Builder;

@Builder
public record ResponseMemberInfoDto(
    String nickName,
    Gender gender,
    String[] categories,
    int age,
    String introduction,
    String profileImgUrl
) {

}
