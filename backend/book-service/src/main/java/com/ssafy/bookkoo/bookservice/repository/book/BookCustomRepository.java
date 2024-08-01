package com.ssafy.bookkoo.bookservice.repository.book;

import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.bookservice.dto.book.RequestSearchBooksFilterDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import java.util.List;

/**
 * 커스텀 책 레포지토리 인터페이스로, 다양한 조건을 기반으로 책을 검색하는 메서드를 정의합니다.
 */
public interface BookCustomRepository {

    /**
     * 검색 종류, 검색 내용, 페이지 및 페이지 내 개수를 기반으로 책을 검색합니다.
     *
     * @param type    검색 종류 (title, author, publisher 등)
     * @param content 검색 내용
     * @param offset  페이지 오프셋
     * @param limit   페이지 내 개수
     * @return 검색된 책 리스트
     */
    List<Book> findByConditions(String type, String content, int offset, int limit);

    /**
     * RequestSearchBooksFilterDto를 기반으로 책을 검색합니다.
     *
     * @param dto RequestSearchBooksFilterDto 객체
     * @return 검색된 책 리스트
     */
    List<Book> findByConditions(RequestSearchBooksFilterDto dto);

    /**
     * ISBN을 기반으로 책을 검색합니다.
     *
     * @param isbn 책의 ISBN
     * @return 검색된 책, 없으면 null 반환
     */
    Book findByIsbn(String isbn);

    /**
     * RequestSearchBookMultiFieldDto를 기반으로 여러 조건을 적용하여 책을 검색합니다.
     *
     * @param filterDto RequestSearchBookMultiFieldDto 객체
     * @return 검색된 책 리스트
     */
    List<Book> findByConditions(RequestSearchBookMultiFieldDto filterDto);
}
