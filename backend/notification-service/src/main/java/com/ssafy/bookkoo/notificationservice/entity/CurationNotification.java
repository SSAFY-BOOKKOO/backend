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
@DiscriminatorValue("curation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurationNotification extends Notification {

    @Column(name = "curation_id")
    private Long curationId;

    @Column(name = "from_member_id")
    private String writer;

    @Builder
    public CurationNotification(Long id, String memberId, Long curationId, String writer) {
        super(id, memberId);
        this.curationId = curationId;
        this.writer = writer;
    }
}
