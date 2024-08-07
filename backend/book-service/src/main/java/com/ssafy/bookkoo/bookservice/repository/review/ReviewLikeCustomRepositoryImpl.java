package com.ssafy.bookkoo.bookservice.repository.review;

import static com.ssafy.bookkoo.bookservice.entity.QReviewLike.reviewLike;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class ReviewLikeCustomRepositoryImpl implements ReviewLikeCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 멤버와 연관된 리뷰 라이크 삭제
     *
     * @param memberId 멤버 ID
     */
    @Override
    @Transactional
    public void deleteByMemberId(Long memberId) {
        queryFactory.delete(reviewLike)
                    .where(reviewLike.id.memberId.eq(memberId))
                    .execute();
    }
}
