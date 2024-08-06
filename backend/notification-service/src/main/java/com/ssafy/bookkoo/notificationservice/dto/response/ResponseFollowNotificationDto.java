package com.ssafy.bookkoo.notificationservice.dto.response;

import com.ssafy.bookkoo.notificationservice.client.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.notificationservice.entity.FollowNotification;
import com.ssafy.bookkoo.notificationservice.enums.NotificationType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseFollowNotificationDto extends ResponseNotificationDto {

    //팔로워 ID
    private String memberId;

    //팔로우한 사람의 닉네임
    private String nickName;

    @Builder
    public ResponseFollowNotificationDto(
        Long notificationId,
        NotificationType notificationType,
        LocalDateTime createAt,
        String memberId, String nickName) {
        super(notificationId, notificationType, createAt);
        this.memberId = memberId;
        this.nickName = nickName;
    }

    public static ResponseFollowNotificationDto toDto(
        FollowNotification followNotification,
        ResponseMemberInfoDto memberInfoDto) {
        return ResponseFollowNotificationDto.builder()
                                            .notificationId(followNotification.getId())
                                            .notificationType(NotificationType.follow)
                                            .memberId(memberInfoDto.memberId())
                                            .nickName(memberInfoDto.nickName())
                                            .build();
    }
}