package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.dto.RequestBookDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import java.util.List;

public interface LibraryService {

    ResponseLibraryDto addLibrary(RequestCreateLibraryDto library);

    List<ResponseLibraryDto> getLibrariesOfMember(Long memberId);

    ResponseLibraryDto getLibrary(Long libraryId);

    ResponseLibraryDto updateLibrary(
        Long libraryId,
        RequestUpdateLibraryDto library
    );

//    ResponseLibraryDto deleteLibrary(Long libraryId);

    Object addBookToLibrary(
        Long libraryId,
        RequestBookDto book
    );

    Integer countBooksInLibrary(Long memberId);
}
