package com.ssafy.bookkoo.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@DiscriminatorValue("follow")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowNotification extends Notification {

    @Column(name = "from_member_id")
    private Long followerId;

    @Builder
    public FollowNotification(Long id, Long memberId, Long followerId) {
        super(id, memberId);
        this.followerId = followerId;
    }
}
