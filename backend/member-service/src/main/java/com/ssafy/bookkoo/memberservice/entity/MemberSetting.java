package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.enums.ReviewVisibility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSetting {

    @Id
    @Column(name = "id")
    private Long id;

    //레터 이메일 수신 여부
    @Setter
    @Column(name = "is_letter_receive")
    private Boolean isLetterReceive;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "review_visibility")
    private ReviewVisibility reviewVisibility;

    @Builder
    public MemberSetting(Long id, Boolean isLetterReceive, ReviewVisibility reviewVisibility) {
        this.id = id;
        this.isLetterReceive = isLetterReceive;
        this.reviewVisibility = reviewVisibility;
    }
}
