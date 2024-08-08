package com.ssafy.bookkoo.booktalkservice.repository;

import static com.ssafy.bookkoo.booktalkservice.entity.QBookTalk.bookTalk;
import static com.ssafy.bookkoo.booktalkservice.entity.QBookTalkMemberMapper.bookTalkMemberMapper;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BookTalkMapperCustomRepositoryImpl implements
    BookTalkMapperCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 사용자 아이디로 참여한 북톡 책 ID 가져오기
     *
     * @param memberId 사용자 ID
     * @return List(책 ID)
     */
    @Override
    public List<Long> findBookIdsByMemberId(Long memberId) {
        BooleanBuilder predicate = new BooleanBuilder();
        // memberId가 일치하는지 확인
        predicate.and(bookTalkMemberMapper.memberId.eq(memberId));

        return queryFactory.select(bookTalkMemberMapper.booktalk.book)
                           .from(bookTalkMemberMapper)
                           .join(bookTalkMemberMapper.booktalk, bookTalk)
                           .where(predicate)
                           .orderBy(bookTalkMemberMapper.booktalk.totalMessageCount.desc())
                           .fetch();
    }
}
