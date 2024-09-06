package com.ssafy.bookkoo.curationservice.dto;

import com.ssafy.bookkoo.curationservice.entity.Gender;
import lombok.Builder;

@Builder
public record ResponseMemberInfoDto(
    String memberId,
    String nickName,
    Gender gender,
    String[] categories,
    Integer age,
    String introduction,
    String profilImgUrl
) {

}
