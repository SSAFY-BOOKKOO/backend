package com.ssafy.bookkoo.bookservice.dto.other;

import com.ssafy.bookkoo.bookservice.entity.Gender;
import java.util.List;
import lombok.Builder;

@Builder
public record ResponseMemberInfoDto(
    String nickName,
    Gender gender,
    List<Integer> categories,
    int age,
    String introduction,
    String profilImgUrl
) {

}
