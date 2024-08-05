package com.ssafy.bookkoo.notificationservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCommunityNotificationDto extends ResponseNotificationDto {

    //커뮤니티 ID
    private Long communityId;

    //커뮤니티 책 이름
    private String title;
}
