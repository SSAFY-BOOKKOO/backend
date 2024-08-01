package com.ssafy.bookkoo.bookservice.repository.book;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.dto.book.SearchBookConditionDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.entity.QBook;
import com.ssafy.bookkoo.bookservice.exception.InvalidAttributeException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

/**
 * BookCustomRepository의 구현체로, 책 검색 관련 커스텀 쿼리를 처리합니다.
 */
@Repository
@AllArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 책 검색(검색 종류, 검색 내용, 페이지, 페이지 내 개수)
     *
     * @param type    검색 종류 (title, author, publisher 등)
     * @param content 검색 내용
     * @param offset  페이지 오프셋
     * @param limit   페이지 내 개수
     * @return 검색된 책 리스트
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
            // type이 title, author, publisher일 경우에 따라 content를 조건에 동적 추가
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

    /**
     * 책 검색 필터 DTO를 기반으로 책 목록을 검색합니다.
     *
     * @param dto RequestSearchBooksFilterDto 객체
     * @return 검색된 책 리스트
     */
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

    /**
     * ISBN을 기반으로 책을 검색합니다.
     *
     * @param isbn ISBN 문자열
     * @return 검색된 책, 없으면 null 반환
     */
    @Override
    public Book findByIsbn(String isbn) {
        QBook book = QBook.book;
        return queryFactory.selectFrom(book)
                           .where(book.isbn.eq(isbn))
                           .fetchFirst(); // 찾지 못하면 null 반환
    }

    /**
     * 여러 필드를 기반으로 책 목록을 검색합니다.
     *
     * @param dto RequestSearchBookMultiFieldDto 객체
     * @return 검색된 책 리스트
     */
    @Override
    public List<Book> findByConditions(RequestSearchBookMultiFieldDto dto) {
        QBook book = QBook.book;
        // 동적 쿼리 시작
        BooleanBuilder predicate = new BooleanBuilder();

        PathBuilder<Book> entityPath = new PathBuilder<>(Book.class, "book");

        // 각 조건 별로 쿼리 조건 추가
        for (SearchBookConditionDto condition : dto.conditions()) {
            try {
                // 필드 유효성 검사
                if (condition.field() != null && condition.values() != null && !condition.values()
                                                                                         .isEmpty()) {
                    // title, author, publisher에 대해서는 like 쿼리
                    if (condition.field()
                                 .equalsIgnoreCase("title") ||
                        condition.field()
                                 .equalsIgnoreCase("author") ||
                        condition.field()
                                 .equalsIgnoreCase("publisher")) {
                        // LIKE 쿼리
                        for (String value : condition.values()) {
                            BooleanExpression likeExpression = entityPath.getString(
                                                                             condition.field())
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
            } catch (InvalidDataAccessApiUsageException | PathElementException ex) {
                throw new InvalidAttributeException("잘못된 속성 이름이 사용되었습니다 : " + condition.field());
            }
        }
        return queryFactory.selectFrom(book)
                           .where(predicate)
                           .offset(dto.offset())
                           .limit(dto.limit())
                           .fetch();
    }
}
