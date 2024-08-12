package com.ssafy.bookkoo.bookservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike {

    @EmbeddedId
    private ReviewMemberId id;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id", insertable = false, updatable = false)
    private Review review;

    @Builder
    public ReviewLike(Review review, Long memberId) {
        this.id = new ReviewMemberId(review.getId(), memberId);
        this.review = review;
    }
}
