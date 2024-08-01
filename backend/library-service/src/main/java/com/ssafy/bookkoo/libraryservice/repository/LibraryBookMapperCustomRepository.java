package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;

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
    List<LibraryBookMapper> findByLibraryIdWithFilter(Long libraryId, Status filter);

    /**
     * 특정 멤버 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 책 ID 리스트
     */
    List<Long> findBookIdsByMemberId(Long memberId);
}
