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
     * 북톡 모든 책 ID 가져오기
     *
     * @return List(책 ID)
     */
    @Override
    public List<Long> findAllBookIds() {
        BooleanBuilder predicate = new BooleanBuilder();

        return queryFactory.select(bookTalkMemberMapper.booktalk.book)
                           .from(bookTalkMemberMapper)
                           .join(bookTalkMemberMapper.booktalk, bookTalk)
                           .where(predicate)
                           .orderBy(bookTalkMemberMapper.booktalk.totalMessageCount.desc())
                           .fetch();
    }
}
