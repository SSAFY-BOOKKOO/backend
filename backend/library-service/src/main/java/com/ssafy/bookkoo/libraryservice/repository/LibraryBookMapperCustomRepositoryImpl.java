package com.ssafy.bookkoo.libraryservice.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.QLibrary;
import com.ssafy.bookkoo.libraryservice.entity.QLibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * LibraryBookMapper 엔티티에 대한 사용자 정의 쿼리 메서드를 구현하는 클래스입니다.
 */
@Repository
@AllArgsConstructor
public class LibraryBookMapperCustomRepositoryImpl implements LibraryBookMapperCustomRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 특정 멤버 ID에 대한 서재 개수를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 서재 개수
     */
    @Override
    public Integer countLibrariesByMemberId(Long memberId) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        QLibrary library = QLibrary.library;

        // memberId에 대해 서재 개수 반환
        Long count = queryFactory.select(libraryBookMapper.count())
                                 .from(libraryBookMapper)
                                 .join(libraryBookMapper.library, library)
                                 .where(library.memberId.eq(memberId))
                                 .fetchOne();

        // null 처리
        return count != null ? count.intValue() : 0;
    }

    /**
     * 특정 서재 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param libraryId 서재 ID
     * @return 책 ID 리스트
     */
    @Override
    public List<Long> findBookIdsByLibraryId(Long libraryId) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(libraryBookMapper.library.id.eq(libraryId))
                           .orderBy(libraryBookMapper.bookOrder.asc())
                           .fetch();
    }

    /**
     * 특정 서재 ID와 상태 필터에 따라 필터링된 LibraryBookMapper 엔티티 리스트를 반환합니다.
     *
     * @param libraryId 서재 ID
     * @param filter    상태 필터
     * @return 필터링된 LibraryBookMapper 엔티티 리스트
     */
    @Override
    public List<LibraryBookMapper> findByLibraryIdWithFilter(Long libraryId, Status filter) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        BooleanBuilder predicate = new BooleanBuilder();

        // 해당 서재와 연결된 책 쿼리
        predicate.and(libraryBookMapper.id.libraryId.eq(libraryId));

        // 필터가 null이 아닐 시 status로 필터
        if (filter != null) {
            predicate.and(libraryBookMapper.status.eq(filter));
        }

        return queryFactory.select(libraryBookMapper)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .orderBy(libraryBookMapper.bookOrder.asc())
                           .fetch();
    }

    /**
     * 특정 멤버 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 책 ID 리스트
     */
    @Override
    public List<Long> findBookIdsByMemberId(Long memberId) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        QLibrary library = QLibrary.library;

        BooleanBuilder predicate = new BooleanBuilder();
        // memberId가 일치하는지 확인
        predicate.and(library.memberId.eq(memberId));

        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .join(libraryBookMapper.library, library)
                           .where(predicate)
                           .orderBy(libraryBookMapper.bookOrder.asc())
                           .fetch();
    }
}
