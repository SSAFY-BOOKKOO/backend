package com.ssafy.bookkoo.booktalkservice.repository;

import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalkMemberMapper;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTalkMapperRepository extends JpaRepository<BookTalkMemberMapper, Long>,
    BookTalkMapperCustomRepository {

    /**
     * 사용자가 참여한 독서록을 찾기 위한 메서드
     *
     * @param memberId : 사용자 ID (PassPort)
     * @return 매퍼 리스트
     */
    List<BookTalkMemberMapper> findByMemberId(Long memberId);


    /**
     * 사용자가 해당 독서록에 참여하고 있는지 확인하기 위한 메서드
     *
     * @param memberId : 사용자 ID (PassPort)
     * @param booktalk : 독서록 번호
     * @return True or False
     */
    Boolean existsByMemberIdAndBooktalk(Long memberId, BookTalk booktalk);

    List<BookTalkMemberMapper> findByBooktalk(BookTalk booktalk);

}

