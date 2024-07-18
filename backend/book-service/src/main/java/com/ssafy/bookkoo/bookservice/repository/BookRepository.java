package com.ssafy.bookkoo.bookservice.repository;

import com.ssafy.bookkoo.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
