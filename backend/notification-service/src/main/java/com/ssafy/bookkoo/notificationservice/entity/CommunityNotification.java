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

    @Column(name = "title")
    private String title;

    @Builder
    public CommunityNotification(Long id, Long memberId, Long communityId, String title) {
        super(id, memberId);
        this.communityId = communityId;
        this.title = title;
    }
}
