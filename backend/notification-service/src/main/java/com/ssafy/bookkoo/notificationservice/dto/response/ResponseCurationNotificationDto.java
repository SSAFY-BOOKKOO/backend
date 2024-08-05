package com.ssafy.bookkoo.notificationservice.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ResponseCurationNotificationDto extends ResponseNotificationDto {

    //큐레이션 레터 작성자의 아이디
    private String memberId;

    //큐레이션 레터 작성자의 닉네임
    private String nickName;

    //큐레이션 ID
    private Long curationId;
}
