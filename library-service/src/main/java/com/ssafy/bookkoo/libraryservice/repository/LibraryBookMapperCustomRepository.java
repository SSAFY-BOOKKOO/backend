package com.ssafy.bookkoo.libraryservice.repository;

import com.querydsl.core.Tuple;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

/**
 * LibraryBookMapper 엔티티에 대한 사용자 정의 쿼리 메서드를 제공하는 인터페이스입니다.
 */
public interface LibraryBookMapperCustomRepository {

    /**
     * 특정 멤버 ID에 대한 서재 개수를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 서재 개수
     */
    Integer countLibrariesByMemberId(Long memberId);

    /**
     * 특정 서재 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param libraryId 서재 ID
     * @return 책 ID 리스트
     */
    List<Long> findBookIdsByLibraryId(Long libraryId);

    /**
     * 특정 서재 ID와 상태 필터에 따라 필터링된 LibraryBookMapper 엔티티 리스트를 반환합니다.
     *
     * @param libraryId 서재 ID
     * @param filter    상태 필터
     * @return 필터링된 LibraryBookMapper 엔티티 리스트
     */
    List<LibraryBookMapper> findByLibraryIdWithFilter(
        Long libraryId,
        Status filter,
        Pageable pageable
    );

    /**
     * 특정 멤버 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 책 ID 리스트
     */
    List<Tuple> findBookIdsByMemberId(Long memberId);

    /**
     * memberId 와 bookID 리스트를 가지고 내 서재에 등록된 책인지 여부를 반환
     *
     * @param memberId 본인 ID
     * @param bookIds  book ID 리스트
     * @return List<Long> 갖고있는 책 ID
     */
    List<Long> findBookIdsByMemberIdAndBookIds(
        Long memberId,
        List<Long> bookIds
    );

    /**
     * 해당 서재의 가장 큰 book Order 값 찾기
     *
     * @param libraryId : 서재 ID
     * @return 가장 큰 Book Order
     */
    Integer findMaxBookOrderByLibraryId(Long libraryId);

    /**
     * 내가 서재에 추가한 최신 책 다섯권 id 반환
     *
     * @param memberId 사용자 ID
     * @return List(책 ID)
     */
    List<Long> findBookIdsByMemberIdLimitFive(Long memberId);

    /**
     * 멤버가 서재에 넣은 책 중 READ 이면서 읽은 기간이 파라미터 범위에 해당되는 책의 ID 가져오기
     *
     * @param memberId 멤버 ID
     * @param startAt  시작일
     * @param endAt    끝일
     * @param status   상태
     * @return memberId 리스트
     */
    List<Long> findBookIdsByStatsCategoriesCondition(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt,
        Status status
    );

    /**
     * 멤버가 서재에 넣은 책 중 updatedAt이 파라미터 범위에 해당되는 책 ID 가져오기
     *
     * @param memberId 멤버 ID
     * @param startAt  시작일
     * @param endAt    끝일
     * @return List<Long>
     */
    List<Long> findBookIdsByMemberIdUpdatedAt(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt
    );

    /**
     * 시간 내에 읽은 권수
     *
     * @param memberId memberId
     * @param startAt  startAt
     * @param endAt    endAt
     * @return 읽은 권수
     */
    Integer countBooksByMemberIdDuration(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt
    );

    /**
     * 서재에서 가장 작은 빈 값 찾기
     *
     * @param libraryId 서재 ID
     * @return 가장 작은 빈 bookOrder
     */
    Integer findFirstMissingBookOrderByLibraryId(Long libraryId);
}
