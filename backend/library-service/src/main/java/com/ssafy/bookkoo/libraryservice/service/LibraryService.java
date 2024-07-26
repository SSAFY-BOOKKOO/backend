package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import java.util.List;

public interface LibraryService {

    ResponseLibraryDto addLibrary(RequestCreateLibraryDto library, Long memberId);

    List<ResponseLibraryDto> getLibrariesOfMember(String nickname);

    ResponseLibraryDto getLibrary(Long libraryId, String filter);

    ResponseLibraryDto updateLibrary(
        Long libraryId,
        RequestUpdateLibraryDto library
    );

//    ResponseLibraryDto deleteLibrary(Long libraryId);

    void addBookToLibrary(
        Long libraryId,
        RequestLibraryBookMapperCreateDto libraryBookMapperDto,
        Long memberId
    );

    Integer countBooksInLibrary(Long memberId);
}
