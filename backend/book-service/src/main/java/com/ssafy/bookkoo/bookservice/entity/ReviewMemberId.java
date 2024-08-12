package com.ssafy.bookkoo.bookservice.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReviewMemberId implements Serializable {

    private Long reviewId;
    private Long memberId;
}
