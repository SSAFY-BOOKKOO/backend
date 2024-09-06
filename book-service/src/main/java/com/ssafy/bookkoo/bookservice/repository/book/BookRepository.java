package com.ssafy.bookkoo.bookservice.repository.book;

import com.ssafy.bookkoo.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Book 엔티티에 대한 기본 JPA 레포지토리와 커스텀 레포지토리를 결합한 인터페이스입니다.
 */
public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

    /**
     * 주어진 ISBN이 존재하는지 확인합니다.
     *
     * @param isbn 책의 ISBN
     * @return ISBN이 존재하면 true, 존재하지 않으면 false
     */
    Boolean existsByIsbn(String isbn);
}
