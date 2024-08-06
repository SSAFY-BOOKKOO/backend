package com.ssafy.bookkoo.notificationservice.dto.response;

import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseCurationNotificationDto extends ResponseNotificationDto {

    //큐레이션 레터 작성자의 아이디
    private String memberId;

    //큐레이션 레터 작성자의 닉네임
    private String nickName;

    //큐레이션 ID
    private Long curationId;

    @Builder
    public ResponseCurationNotificationDto(Long notificationId,
        NotificationType notificationType,
        LocalDateTime createdAt, String memberId, String nickName, Long curationId) {
        super(notificationId, notificationType, createdAt);
        this.memberId = memberId;
        this.nickName = nickName;
        this.curationId = curationId;
    }
}
