package com.ssafy.bookkoo.booktalkservice.repository;

import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookTalkRepository extends JpaRepository<BookTalk, Long> {

    /**
     * @param book : 책 번호
     * @return 독서록
     */
    Optional<BookTalk> findByBook(Long book);

    /**
     * 같은 책으로 등록된 독서록이 있는지 확인하기 위한 메서드
     *
     * @param book : 책 번호
     * @return True or False
     */
    Boolean existsByBook(Long book);


    /**
     * 사용자가 참여하고 있는 독서록 중 채팅이 많은 순으로 가져오는 메서드
     *
     * @param ids      : 사용자가 참여중인 독서록 id List
     * @param pageable : 페이징 번호
     * @return 독서록 리스트
     */
    List<BookTalk> findByIdInOrderByTotalMessageCountDesc(List<Long> ids, Pageable pageable);

    /**
     * 사용자가 참여하고 있는 독서록 중 최근 채팅순으로 가져오는 메서드
     *
     * @param ids      : 사용자가 참여중인 독서록 id List
     * @param pageable : 페이징 번호
     * @return 독서록 리스트
     */
    List<BookTalk> findByIdInOrderByUpdatedAtDesc(List<Long> ids, Pageable pageable);

    /**
     * 오늘 가장 채팅이 많이 달린 독서록을 가져오는 메ㅅ드
     *
     * @param pageable : 페이징 번호
     * @return 독서록 리스트
     */
    List<BookTalk> findAllByOrderByDayMessageCountDesc(Pageable pageable);

    List<BookTalk> findByUpdatedAtBefore(LocalDateTime time);

    @Modifying
    @Query("UPDATE BookTalk b SET b.dayMessageCount = 0")
    void dayCountDelete();
}
