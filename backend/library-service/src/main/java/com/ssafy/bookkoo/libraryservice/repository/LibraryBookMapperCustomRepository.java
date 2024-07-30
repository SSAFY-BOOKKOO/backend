package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;

public interface LibraryBookMapperCustomRepository {

    Integer countLibrariesByMemberId(Long memberId);

    List<Long> findBookIdsByLibraryId(Long libraryId);

    List<LibraryBookMapper> findByLibraryIdWithFilter(
        Long libraryId,
        Status filter
    );

    List<Long> findBookIdsByMemberId(
        Long memberId
    );
}
