package com.ssafy.bookkoo.bookservice.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
    public List<Book> findByConditions(String type, String content, int offset, int limit) {
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
    public Book findByIsbn(String isbn) {
        QBook book = QBook.book;
        return queryFactory.selectFrom(book)
                           .where(book.isbn.eq(isbn))
                           .fetchFirst(); // 찾지 못하면 null 반환
    }
}
