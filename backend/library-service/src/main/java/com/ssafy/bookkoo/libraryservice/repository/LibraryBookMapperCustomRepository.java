package com.ssafy.bookkoo.libraryservice.repository;

import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;

public interface LibraryBookMapperCustomRepository {

    Integer countLibrariesByMemberId(Long memberId);

    List<Long> findBookIdsByLibraryId(Long libraryId);

    List<Long> findBookIdsByLibraryIdWithFilter(
        Long libraryId,
        Status filter
    );
}
