package com.ssafy.bookkoo.bookservice.repository.review;

import static com.ssafy.bookkoo.bookservice.entity.QReview.review;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Review> findByBookIdExceptMine(Long memberId, Long bookId) {
        BooleanBuilder predicate = new BooleanBuilder();

        // 해당 책에 대한 리뷰 && 내 꺼 아닌 리뷰
        predicate.and(review.book.id.eq(bookId));
        predicate.and(review.memberId.ne(memberId));

        return queryFactory.selectFrom(review)
                           .where(predicate)
                           .fetch();
    }

    /**
     * 책에 대해 내가 작성할 수 있는 리뷰는 한 개 이기 떄문에 이거에 대해 작성한 리뷰가 있는지 여부를 확인하는 쿼리
     *
     * @param memberId 회원 ID
     * @param bookId   책 ID
     * @return true/false
     */
    @Override
    public Boolean existsByBookIdAndMine(Long memberId, Long bookId) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(review.book.id.eq(bookId));
        predicate.and(review.memberId.eq(memberId));

        Integer fetchOne = queryFactory.selectOne()
                                       .from(review)
                                       .where(predicate)
                                       .fetchFirst();

        return fetchOne != null;
    }

    /**
     * memberId, bookId로 리뷰 찾기
     *
     * @param bookId   책 ID
     * @param memberId memberId
     * @return review
     */
    @Override
    public Review findByBookIdMemberId(Long bookId, Long memberId) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(review.book.id.eq(bookId));
        predicate.and(review.memberId.eq(memberId));

        return queryFactory.selectFrom(review)
                           .where(predicate)
                           .fetchOne();
    }
}
