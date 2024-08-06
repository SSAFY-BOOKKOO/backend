package com.ssafy.bookkoo.notificationservice.dto.response;

import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseCommunityNotificationDto extends ResponseNotificationDto {

    //커뮤니티 ID
    private Long communityId;

    //커뮤니티 책 이름
    private String title;

    @Builder
    public ResponseCommunityNotificationDto(Long notificationId,
        NotificationType notificationType,
        LocalDateTime createdAt, Long communityId, String title) {
        super(notificationId, notificationType, createdAt);
        this.communityId = communityId;
        this.title = title;
    }
}
