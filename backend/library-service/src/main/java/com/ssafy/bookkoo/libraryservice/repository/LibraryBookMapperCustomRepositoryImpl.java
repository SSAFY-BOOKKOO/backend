package com.ssafy.bookkoo.libraryservice.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bookkoo.libraryservice.entity.QLibrary;
import com.ssafy.bookkoo.libraryservice.entity.QLibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LibraryBookMapperCustomRepositoryImpl implements LibraryBookMapperCustomRepository {

    private final JPAQueryFactory queryFactory;

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

    @Override
    public List<Long> findBookIdsByLibraryId(Long libraryId) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(libraryBookMapper.library.id.eq(libraryId))
                           .fetch();
    }

    @Override
    public List<Long> findBookIdsByLibraryIdWithFilter(
        Long libraryId,
        Status filter
    ) {
        QLibraryBookMapper libraryBookMapper = QLibraryBookMapper.libraryBookMapper;
        BooleanBuilder predicate = new BooleanBuilder();

        // 해당 서재와 연결된 책 쿼리
        predicate.and(libraryBookMapper.library.id.eq(libraryId));

        // 필터가 null이 아닐 시 status로 필터
        if (filter != null) {
            predicate.and(libraryBookMapper.status.eq(filter));
        }

        return queryFactory.select(libraryBookMapper.id.bookId)
                           .from(libraryBookMapper)
                           .where(predicate)
                           .fetch();
    }
}
