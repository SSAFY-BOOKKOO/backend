package com.ssafy.bookkoo.memberservice.dto.response;

import java.util.List;

public record ResponseMemberProfileDto(
    String email,
    String nickName,
    String profileImgUrl,
    String introduction,
    List<Integer> categories
) {

}
