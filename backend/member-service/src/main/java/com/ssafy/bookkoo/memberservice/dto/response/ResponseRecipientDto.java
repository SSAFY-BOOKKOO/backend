package com.ssafy.bookkoo.memberservice.dto.response;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
public class ResponseRecipientDto {

    private Long memberId;

    private String email;

    private Boolean isReceiveEmail;

    @Builder
    @QueryProjection
    public ResponseRecipientDto(Long memberId, String email, Boolean isReceiveEmail) {
        this.memberId = memberId;
        this.email = email;
        this.isReceiveEmail = isReceiveEmail;
    }
}
