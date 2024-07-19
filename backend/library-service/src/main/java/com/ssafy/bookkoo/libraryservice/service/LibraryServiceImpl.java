package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.exception.LibraryNotFoundException;
import com.ssafy.bookkoo.libraryservice.mapper.LibraryMapper;
import com.ssafy.bookkoo.libraryservice.repository.LibraryBookMapperRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryStyleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryStyleRepository libraryStyleRepository;
    private final LibraryBookMapperRepository libraryBookMapperRepository;

    private final LibraryMapper libraryMapper = LibraryMapper.INSTANCE;

    @Override
    @Transactional
    public ResponseLibraryDto addLibrary(RequestCreateLibraryDto libraryDto) {
        Library library = libraryMapper.toEntity(libraryDto);

        Library savedLibrary = libraryRepository.save(library);

        return libraryMapper.toResponseDto(savedLibrary);
    }

    @Override
    @Transactional
    public List<ResponseLibraryDto> getLibraries() {
        List<Library> libraries = libraryRepository.findAll();
        return libraryMapper.toResponseDtoList(libraries);
    }

    @Override
    @Transactional
    public ResponseLibraryDto getLibrary(Long libraryId) {
        Library library = findLibraryByIdWithException(libraryId);
        return libraryMapper.toResponseDto(library);
    }

    @Override
    @Transactional
    public ResponseLibraryDto updateLibrary(Long libraryId, RequestUpdateLibraryDto libraryDto) {
        Library libraryToUpdate = findLibraryByIdWithException(libraryId);
        
        return null;
    }

    @Override
    @Transactional
    public Object addBookToLibrary(Long libraryId, Object book) {
        return null;
    }

    @Override
    @Transactional
    public Integer countBooksInLibrary(Long memberId) {
        return 0;
    }


    private Library findLibraryByIdWithException(Long libraryId) {
        return libraryRepository.findById(libraryId)
                                .orElseThrow(() -> new LibraryNotFoundException(
                                    "Library not found with id : " + libraryId));
    }
}
