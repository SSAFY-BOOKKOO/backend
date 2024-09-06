package com.ssafy.bookkoo.libraryservice.repository;

import static com.ssafy.bookkoo.libraryservice.entity.QLibrary.library;
import static com.ssafy.bookkoo.libraryservice.entity.QLibraryBookMapper.libraryBookMapper;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public List<LibraryBookMapper> findByLibraryIdWithFilter(
        Long libraryId,
        Status filter,
        Pageable pageable
    ) {

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
                           .offset(pageable.getOffset())
                           .limit(pageable.getPageSize())
                           .fetch();
    }

    /**
     * 특정 멤버 ID에 대한 책 ID 리스트를 반환합니다.
     *
     * @param memberId 멤버 ID
     * @return 책 ID 리스트
     */
    @Override
    public List<Tuple> findBookIdsByMemberId(Long memberId) {

        BooleanBuilder predicate = new BooleanBuilder();
        // memberId가 일치하는지 확인
        predicate.and(library.memberId.eq(memberId));

        return queryFactory.select(libraryBookMapper.id.bookId, libraryBookMapper.id.libraryId)
                           .from(libraryBookMapper)
                           .join(libraryBookMapper.library, library)
                           .where(predicate)
                           .orderBy(libraryBookMapper.bookOrder.asc())
                           .fetch();
    }

    /**
     * memberId 와 bookID 리스트를 가지고 내 서재에 등록된 책인지 여부를 반환
     *
     * @param memberId 본인 ID
     * @param bookIds  book ID 리스트
     * @return List<Long> 갖고있는 책 ID
     */
    @Override
    public List<Long> findBookIdsByMemberIdAndBookIds(
        Long memberId,
        List<Long> bookIds
    ) {

        BooleanBuilder predicate = new BooleanBuilder();

        // 내 서재 찾기
        predicate.and(libraryBookMapper.library.memberId.eq(memberId));

        // 책 찾기
        predicate.and(libraryBookMapper.id.bookId.in(bookIds));

        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .fetch();
    }

    /**
     * 해당 서재의 가장 큰 book Order 값 찾기
     *
     * @param libraryId : 서재 ID
     * @return 가장 큰 Book Order
     */
    @Override
    public Integer findMaxBookOrderByLibraryId(Long libraryId) {

        BooleanBuilder predicate = new BooleanBuilder();

        predicate.and(libraryBookMapper.id.libraryId.eq(libraryId));
        Integer maxBookOrder = queryFactory.select(libraryBookMapper.bookOrder.max())
                                           .from(libraryBookMapper)
                                           .where(predicate)
                                           .fetchOne();

        return maxBookOrder != null ? maxBookOrder : 0; // null인 경우 0반환
    }

    /**
     * 내가 서재에 추가한 최신 책 다섯권 id 반환
     *
     * @param memberId 사용자 ID
     * @return List(책 ID)
     */
    @Override
    public List<Long> findBookIdsByMemberIdLimitFive(Long memberId) {

        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(library.memberId.eq(memberId));
        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .orderBy(libraryBookMapper.createdAt.desc())
                           .limit(5)
                           .fetch();
    }

    /**
     * 멤버가 서재에 넣은 책 중 READ 이면서 읽은 기간이 파라미터 범위에 해당되는 책의 ID 가져오기
     *
     * @param memberId 멤버 ID
     * @param startAt  시작일
     * @param endAt    끝일
     * @param status   상태
     * @return memberId 리스트
     */
    @Override
    // 이걸 사용하는 측에서 다른 서비스와의 통신이 있기 때문에 레퍼지토리 레이어에서 트랜잭션 사용
    @Transactional(readOnly = true)
    public List<Long> findBookIdsByStatsCategoriesCondition(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt,
        Status status
    ) {
        BooleanBuilder predicate = new BooleanBuilder();
        // 1. 내 서재에 대해서만 쿼리
        predicate.and(libraryBookMapper.library.memberId.eq(memberId));

        // 2. status 에 따른 쿼리
        if (status != null) { // 안 들어올 경우 전체 대상
            predicate.and(libraryBookMapper.status.eq(status));
        }

        // 3. 날짜에 대해 쿼리
        if (startAt != null && endAt != null) {
            // 책의 읽기 시작 날짜가 주어진 기간의 끝 날짜 이전이거나 같은 경우
            predicate.and(libraryBookMapper.startAt.loe(endAt));
            // 책의 읽기 끝 날짜가 주어진 기간의 시작 날짜 이후거나 같은 경우
            predicate.and(libraryBookMapper.endAt.goe(startAt));
        }

        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .fetch()
            ;
    }


    /**
     * 멤버가 서재에 넣은 책 중 updatedAt이 파라미터 범위에 해당되는 책 ID 가져오기
     *
     * @param memberId 멤버 ID
     * @param startAt  시작일
     * @param endAt    끝일
     * @return List<Long>
     */
    @Override
    public List<Long> findBookIdsByMemberIdUpdatedAt(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt
    ) {
        BooleanBuilder predicate = new BooleanBuilder();

        // 1. memberId로 필터링
        predicate.and(libraryBookMapper.library.memberId.eq(memberId));

        // startAt, endAt 으로 필터링

        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .fetch();
    }

    /**
     * 시간 내에 읽은 권수
     *
     * @param memberId memberId
     * @param startAt  startAt
     * @param endAt    endAt
     * @return 읽은 권수
     */
    @Override
    public Integer countBooksByMemberIdDuration(
        Long memberId,
        LocalDate startAt,
        LocalDate endAt
    ) {
        BooleanBuilder predicate = new BooleanBuilder();
        // 내 서재
        predicate.and(libraryBookMapper.library.memberId.eq(memberId));

        // 기간 (걸치는 것도 포함!)
        if (startAt != null && endAt != null) {
            predicate.and(libraryBookMapper.startAt.loe(endAt));
            predicate.and(libraryBookMapper.endAt.goe(startAt));
        }

        Long count = queryFactory.select(libraryBookMapper.id.bookId.count())
                                 .from(libraryBookMapper)
                                 .where(predicate)
                                 .fetchOne();

        return count != null ? count.intValue() : 0;
    }

    /**
     * 서재에서 가장 작은 빈 값 찾기
     *
     * @param libraryId 서재 ID
     * @return 가장 작은 빈 bookOrder
     */
    @Override
    @Transactional(readOnly = true)
    public Integer findFirstMissingBookOrderByLibraryId(Long libraryId) {
        // 채워져 있는 순서들을 Set으로 변환하여 검색 최적화
        Set<Integer> filledOrders = new HashSet<>(queryFactory.select(libraryBookMapper.bookOrder)
                                                              .from(libraryBookMapper)
                                                              .where(
                                                                  libraryBookMapper.library.id.eq(
                                                                      libraryId))
                                                              .orderBy(
                                                                  libraryBookMapper.bookOrder.asc())
                                                              .fetch());

        // 1부터 21까지의 범위 내에서 빈 자리를 찾기
        for (int i = 1; i <= 21; i++) {
            if (!filledOrders.contains(i)) {
                return i; // 첫 번째 빈 자리 반환
            }
        }

        return null; // 모든 자리가 채워져 있으면 null 반환
    }
}
