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
}
