package com.ssafy.bookkoo.bookservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.bookservice.dto.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.dto.SearchBookConditionDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.QBook;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 책 검색(검색 종류, 검색 내용, 페이지, 페이지 내 개수)
     *
     * @param type    : 검색 종류
     * @param content : 검색 내용
     * @param offset  : 페이지
     * @param limit   : 페이지 내 개수
     * @return List<Book>
     */
    @Override
    public List<Book> findByConditions(
        String type,
        String content,
        int offset,
        int limit
    ) {
        QBook book = QBook.book;
        BooleanExpression predicate = book.isNotNull();

        if (type != null) {
            // type이 title, author, publisher일 경우 에 따라 content를 조건에 동적 추가
            switch (type) {
                case "title":
                    predicate = predicate.and(book.title.contains(content));
                    break;
                case "author":
                    predicate = predicate.and(book.author.contains(content));
                    break;
                case "publisher":
                    predicate = predicate.and(book.publisher.contains(content));
                    break;
                default:
                    // 기본일 경우 모두 포함
                    break;
            }
        }

        return queryFactory.selectFrom(book)
                           .where(predicate)
                           .offset(offset)
                           .limit(limit)
                           .fetch();
    }

    @Override
    public List<Book> findByConditions(RequestSearchBooksFilterDto dto) {
        QBook book = QBook.book;
        BooleanExpression predicate = book.isNotNull();

        PathBuilder<Book> entityPath = new PathBuilder<>(Book.class, "book");

        if (dto.field() != null && dto.value() != null) {
            BooleanExpression inExpression = entityPath.getString(dto.field())
                                                       .in(dto.value());
            predicate = predicate.and(inExpression);
        }

        return queryFactory.selectFrom(book)
                           .where(predicate)
                           .offset(dto.offset())
                           .limit(dto.limit())
                           .fetch();
    }

    @Override
    public Book findByIsbn(String isbn) {
        QBook book = QBook.book;
        return queryFactory.selectFrom(book)
                           .where(book.isbn.eq(isbn))
                           .fetchFirst(); // 찾지 못하면 null 반환
    }

    @Override
    public List<Book> findByConditions(RequestSearchBookMultiFieldDto dto) {
        QBook book = QBook.book;
        // 동적 쿼리 시작
        BooleanBuilder predicate = new BooleanBuilder();

        PathBuilder<Book> entityPath = new PathBuilder<>(Book.class, "book");

        // 각 조건 별로 쿼리 조건 추가
        for (SearchBookConditionDto condition : dto.conditions()) {
            // 필드 유효성 검사
            if (condition.field() != null && condition.values() != null && !condition.values()
                                                                                     .isEmpty()) {
                // title, author, publisher 에 대해서는 like 쿼리
                if (condition.field()
                             .equalsIgnoreCase("title") || condition.field()
                                                                    .equalsIgnoreCase("author")
                    || condition.field()
                                .equalsIgnoreCase("publisher")) {
                    // LIKE 쿼리
                    for (String value : condition.values()) {
                        BooleanExpression likeExpression = entityPath.getString(condition.field())
                                                                     .likeIgnoreCase(
                                                                         "%" + value + "%");
                        predicate.and(likeExpression);
                    }
                } else {
                    // IN 쿼리
                    BooleanExpression inExpression = entityPath.getString(condition.field())
                                                               .in(condition.values());
                    predicate.and(inExpression);
                }
            }
        }
        return queryFactory.selectFrom(book)
                           .where(predicate)
                           .offset(dto.offset())
                           .limit(dto.limit())
                           .fetch();
    }
}
