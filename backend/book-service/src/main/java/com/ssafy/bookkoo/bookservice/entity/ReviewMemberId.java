package com.ssafy.bookkoo.bookservice.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMemberId {

    private Long reviewId;
    private Long memberId;
}
