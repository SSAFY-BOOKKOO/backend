package com.ssafy.bookkoo.memberservice.dto.response;

import com.ssafy.bookkoo.memberservice.enums.Gender;
import java.util.List;
import lombok.Builder;

@Builder
public record ResponseMemberInfoDto(
    String memberId,
    String nickName,
    Gender gender,
    List<Integer> categories,
    Integer age,
    String introduction,
    String profileImgUrl
) {

}
