package com.ssafy.bookkoo.memberservice.dto.response;

import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import lombok.Builder;
import lombok.Data;

@Data
public class ResponseFollowShipDto {

    private String memberId;

    private String nickName;

    private String profileImgUrl;


    @Builder
    public ResponseFollowShipDto(String memberId, String nickName, String profileImgUrl) {
        this.memberId = memberId;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
    }

    public static ResponseFollowShipDto toDto(MemberInfo memberInfo) {
        return ResponseFollowShipDto.builder()
                                    .memberId(memberInfo.getMemberId())
                                    .nickName(memberInfo.getNickName())
                                    .profileImgUrl(memberInfo.getProfileImgUrl())
                                    .build();
    }
}
