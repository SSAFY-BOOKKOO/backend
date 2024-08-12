package com.ssafy.bookkoo.curationservice.entity;

import com.ssafy.bookkoo.curationservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class CurationSend extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curation_id")
    private Curation curation;

    @Column(nullable = false)
    private Boolean isRead;

    @Column(nullable = false)
    private Boolean isStored;

    public void read() {
        isRead = true;
    }

    public void changeStoredStatus() {
        isStored = !isStored;
    }

    @Builder
    public CurationSend(Long receiver, Curation curation) {
        this.receiver = receiver;
        this.curation = curation;
        this.isRead = false;
        this.isStored = false;
    }

}
