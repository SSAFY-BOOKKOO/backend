package com.ssafy.bookkoo.libraryservice.repository;

public interface LibraryBookMapperCustomRepository {

    Integer countLibrariesByMemberId(Long memberId);
}
