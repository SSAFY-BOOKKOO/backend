package com.ssafy.bookkoo.booktalkservice.repository;

import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTalkRepository extends JpaRepository<BookTalk, Long> {

    Optional<BookTalk> findByBook(Long book);

}
