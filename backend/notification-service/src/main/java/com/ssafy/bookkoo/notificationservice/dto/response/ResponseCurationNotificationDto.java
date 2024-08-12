package com.ssafy.bookkoo.notificationservice.dto.response;

import com.ssafy.bookkoo.notificationservice.client.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.notificationservice.entity.CurationNotification;
import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
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

    public static ResponseCurationNotificationDto toDto(
        CurationNotification curationNotification,
        ResponseMemberInfoDto memberInfoDto) {
        return ResponseCurationNotificationDto.builder()
                                              .notificationId(curationNotification.getId())
                                              .notificationType(NotificationType.curation)
                                              .memberId(memberInfoDto.memberId())
                                              .nickName(memberInfoDto.nickName())
                                              .createdAt(curationNotification.getCreatedAt())
                                              .curationId(curationNotification.getCurationId())
                                              .build();
    }
}
