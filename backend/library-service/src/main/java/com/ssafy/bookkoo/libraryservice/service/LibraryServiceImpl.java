package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.dto.RequestBookDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.entity.LibraryStyle;
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
    private final LibraryMapper libraryMapper;

    @Override
    @Transactional
    public ResponseLibraryDto addLibrary(RequestCreateLibraryDto libraryDto) {
        Library library = libraryMapper.toEntity(libraryDto);
        Library savedLibrary = libraryRepository.save(library);

        LibraryStyle libraryStyle = new LibraryStyle(savedLibrary, libraryDto.libraryStyleDto()
                                                                             .libraryColor());
        libraryStyleRepository.save(libraryStyle);

        return libraryMapper.toResponseDto(savedLibrary);
    }

    @Override
    @Transactional
    public List<ResponseLibraryDto> getLibrariesOfMember(Long memberId) {
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
    public ResponseLibraryDto updateLibrary(
        Long libraryId,
        RequestUpdateLibraryDto libraryDto
    ) {
        // 서재 찾기
        Library libraryToUpdate = findLibraryByIdWithException(libraryId);
        // 서재 업데이트
        libraryMapper.updateLibraryFromDto(libraryDto, libraryToUpdate);
        // 서재 스타일 업데이트 로직
        LibraryStyle libraryStyleToUpdate = libraryToUpdate.getLibraryStyle();
        if (libraryStyleToUpdate == null) {
            // 없을 경우 새로 생성
            libraryStyleToUpdate = new LibraryStyle(libraryToUpdate, libraryDto.libraryStyleDto()
                                                                               .libraryColor());
        } else {
            // 있으면 업데이트
            libraryStyleToUpdate.setLibraryColor(libraryStyleToUpdate.getLibraryColor());
        }
        // 서재 스타일 저장
        libraryStyleRepository.save(libraryStyleToUpdate);
        // 서재 저장
        Library updatedLibrary = libraryRepository.save(libraryToUpdate);

        return libraryMapper.toResponseDto(updatedLibrary);
    }

    @Override
    @Transactional
    public Object addBookToLibrary(
        Long libraryId,
        RequestBookDto bookDto,
        Long memberId
    ) {
        Library library = findLibraryByIdWithException(libraryId);

        // 1. 해당 책이 DB에 있는지 확인 후 없으면 생성하는 api 요청하기

        // 2. 해당 책에 대한 id 값을 받아서 책-서재 매퍼에 추가해 책 등록하기

        return null;
    }

    @Override
    @Transactional
    public Integer countBooksInLibrary(Long memberId) {
        return libraryBookMapperRepository.countLibrariesByMemberId(memberId);
    }


    private Library findLibraryByIdWithException(Long libraryId) {
        return libraryRepository.findById(libraryId)
                                .orElseThrow(() -> new LibraryNotFoundException(
                                    "Library not found with id : " + libraryId));
    }
}
