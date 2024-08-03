package com.ssafy.bookkoo.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("community")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityNotification extends Notification {

    @Column(name = "community_id")
    private Long communityId;

    @Builder
    public CommunityNotification(Long id, String memberId, Long communityId) {
        super(id, memberId);
        this.communityId = communityId;
    }
}
