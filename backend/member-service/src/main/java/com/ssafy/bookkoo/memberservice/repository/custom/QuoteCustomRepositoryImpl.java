package com.ssafy.bookkoo.memberservice.repository.custom;

import static com.ssafy.bookkoo.memberservice.entity.QQuote.quote;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.memberservice.entity.Quote;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QuoteCustomRepositoryImpl implements QuoteCustomRepository {

    private final JPAQueryFactory queryFactory;


    /**
     * 페이징을 통해 멤버의 글귀를 가져옵니다. (최신 순)
     *
     * @param memberId
     * @param pageable
     * @return
     */
    @Override
    public List<Quote> findByMemberId(Long memberId, Pageable pageable) {
        return queryFactory.selectFrom(quote)
                           .where(quote.memberInfo.id.eq(memberId))
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .orderBy(quote.createdAt.desc())
                           .fetch();
    }
}
