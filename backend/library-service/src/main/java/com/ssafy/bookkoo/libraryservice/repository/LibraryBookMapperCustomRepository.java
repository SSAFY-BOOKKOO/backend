package com.ssafy.bookkoo.libraryservice.repository;

import java.util.List;

public interface LibraryBookMapperCustomRepository {

    Integer countLibrariesByMemberId(Long memberId);

    List<Long> findBookIdsByLibraryId(Long libraryId);
}
