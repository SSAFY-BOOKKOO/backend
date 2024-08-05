package com.ssafy.bookkoo.booktalkservice.repository;

import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalkMemberMapper;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTalkMapperRepository extends JpaRepository<BookTalkMemberMapper, Long> {

    List<BookTalkMemberMapper> findByBooktalkId(BookTalk bookTalk);

    List<BookTalkMemberMapper> findByMemberId(Long memberId);

}

