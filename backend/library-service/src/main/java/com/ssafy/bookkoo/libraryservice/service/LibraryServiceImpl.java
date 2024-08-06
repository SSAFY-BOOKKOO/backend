package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.client.BookServiceClient;
import com.ssafy.bookkoo.libraryservice.client.MemberServiceClient;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.ResponseLibraryBookDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.ResponseLibraryBookMapperDto;
import com.ssafy.bookkoo.libraryservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.other.SearchBookConditionDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.LibraryStyle;
import com.ssafy.bookkoo.libraryservice.entity.MapperKey;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import com.ssafy.bookkoo.libraryservice.exception.BookAlreadyMappedException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryBookNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryIsNotYoursException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryLimitExceededException;
import com.ssafy.bookkoo.libraryservice.exception.LibraryNotFoundException;
import com.ssafy.bookkoo.libraryservice.exception.MemberNotFoundException;
import com.ssafy.bookkoo.libraryservice.mapper.LibraryBookMapperMapper;
import com.ssafy.bookkoo.libraryservice.mapper.LibraryMapper;
import com.ssafy.bookkoo.libraryservice.repository.LibraryBookMapperRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryRepository;
import com.ssafy.bookkoo.libraryservice.repository.LibraryStyleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryStyleRepository libraryStyleRepository;
    private final LibraryBookMapperRepository libraryBookMapperRepository;

    private final LibraryMapper libraryMapper;
    private final LibraryBookMapperMapper libraryBookMapperMapper;

    private final BookServiceClient bookServiceClient;
    private final MemberServiceClient memberServiceClient;

    /**
     * 서재 추가
     *
     * @param libraryDto 서재 정보
     * @param memberId   사용자 ID
     * @return ResopnseLibraryDto
     */
    @Override
    @Transactional
    public ResponseLibraryDto addLibrary(
        RequestCreateLibraryDto libraryDto,
        Long memberId
    ) {
        // 현재 사용자 서재 개수 확인
        int currentLibraryCount = libraryRepository.countByMemberId(memberId);

        if (currentLibraryCount >= 3) { // 세개 이상일 경우 예외처리
            throw new LibraryLimitExceededException();
        }
        // library 엔티티 만들기
        Library library = libraryMapper.toEntity(libraryDto, memberId);

        // 서재 스타일 만들기
        LibraryStyle libraryStyle = LibraryStyle.builder()
                                                .libraryColor(libraryDto.libraryStyleDto()
                                                                        .libraryColor())
                                                .library(library)
                                                .build();
        // 서재 스타일 저장하기
        libraryStyleRepository.save(libraryStyle);

        // 서재에 서재 스타일 매핑
        library.setLibraryStyle(libraryStyle);

        // 서재 엔티티 저장하기
        libraryRepository.save(library);

        return libraryMapper.toResponseDto(library);
    }

    /**
     * 사용자의 닉네임으로 해당 사용자의 서재 목록 불러오기
     *
     * @param nickname 사용자 닉네임
     * @return List<ResponseLibraryDto>
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseLibraryDto> getLibrariesOfMember(String nickname) {
        Long memberId;
        try {
            // 사용자의 nickname으로 memberId 찾는 통신하기
            memberId = memberServiceClient.getMemberIdByNickName(nickname);
        } catch (Exception e) { // 못찾으면 에러 처리
            throw new MemberNotFoundException();
        }
        // 찾은 memberId로 서재 목록 가져오기
        List<Library> libraries = libraryRepository.findByMemberId(memberId);
        return libraryMapper.toResponseDtoList(libraries);
    }

    /**
     * 서재 ID로 서재 상세 조회 + 딸린 책 정보까지 조회
     *
     * @param libraryId 서재 ID
     * @param filter    Status (생략 시 전체 조회)
     * @return RespnoseLibraryDto
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseLibraryDto getLibrary(
        Long libraryId,
        Status filter
    ) {
        // 라이브러리 ID로 라이브러리를 찾고 예외를 처리합니다.
        Library library = findLibraryByIdWithException(libraryId);
        // 라이브러리 엔티티를 DTO로 변환합니다.
        ResponseLibraryDto libraryDto = libraryMapper.toResponseDto(library);

        // 라이브러리 ID로 연결된 책 매퍼 목록을 가져옵니다.
        List<LibraryBookMapper> libraryBookMappers = libraryBookMapperRepository.findByLibraryIdWithFilter(
            libraryId, filter);

        // 라이브러리 ID로 연결된 책 ID 목록을 가져옵니다.
        List<Long> bookIds = libraryBookMappers.stream()
                                               .map(l -> l.getId()
                                                          .getBookId())
                                               .toList();
        // 책 ID 목록을 String 목록으로 변환합니다.
        List<String> stringBookIds = bookIds.stream()
                                            .map(String::valueOf)
                                            .collect(
                                                Collectors.toList());
        // 책이 없으면 바로 반환
        if (stringBookIds.isEmpty()) {
            libraryDto = ResponseLibraryDto.builder().
                                           name(libraryDto.name())
                                           .libraryOrder(libraryDto.libraryOrder())
                                           .libraryStyleDto(libraryDto.libraryStyleDto())
                                           .books(List.of())
                                           .build();
            return libraryDto;
        }
        SearchBookConditionDto condition = SearchBookConditionDto.builder()
                                                                 .field("id")
                                                                 .values(stringBookIds)
                                                                 .build();

        // 필터 DTO를 생성합니다.
        RequestSearchBookMultiFieldDto filterDto = RequestSearchBookMultiFieldDto.builder()
                                                                                 .conditions(
                                                                                     List.of(
                                                                                         condition))
                                                                                 .limit(
                                                                                     9) // 이건 바꿔야할듯
                                                                                 .offset(0)
                                                                                 .build();

        // BookServiceClient를 통해 책 정보를 가져옵니다.
        List<ResponseBookDto> books = bookServiceClient.getBooksByCondition(
            filterDto);

        // 책 정보를 매퍼 정보와 매핑합니다.
        List<ResponseLibraryBookMapperDto> libraryBooks = libraryBookMappers.stream()
                                                                            .map(mapper -> {
                                                                                ResponseBookDto bookDto = books.stream()
                                                                                                               .filter(
                                                                                                                   book -> book.id()
                                                                                                                               .equals(
                                                                                                                                   mapper.getId()
                                                                                                                                         .getBookId()))
                                                                                                               .findFirst()
                                                                                                               .orElse(
                                                                                                                   null);
                                                                                return ResponseLibraryBookMapperDto.builder()
                                                                                                                   .book(
                                                                                                                       bookDto)
                                                                                                                   .bookOrder(
                                                                                                                       mapper.getBookOrder())
                                                                                                                   .bookColor(
                                                                                                                       mapper.getBookColor())
                                                                                                                   .startAt(
                                                                                                                       mapper.getStartAt())
                                                                                                                   .endAt(
                                                                                                                       mapper.getEndAt())
                                                                                                                   .status(
                                                                                                                       mapper.getStatus())
                                                                                                                   .rating(
                                                                                                                       mapper.getRating())
                                                                                                                   .build();
                                                                            })
                                                                            .collect(
                                                                                Collectors.toList());

        return ResponseLibraryDto.builder()
                                 .id(libraryId)
                                 .libraryOrder(libraryDto.libraryOrder())
                                 .libraryStyleDto(libraryDto.libraryStyleDto())
                                 .name(libraryDto.name())
                                 .books(libraryBooks)
                                 .build();
    }

    /**
     * 서재 수정
     *
     * @param libraryId  수정할 서재 ID
     * @param libraryDto 수정할 서재 데이터
     * @return ResponseLibraryDto
     */
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

        // 서재 저장
        Library updatedLibrary = libraryRepository.save(libraryToUpdate);

        return libraryMapper.toResponseDto(updatedLibrary);
    }

    /**
     * 서재에 책 넣기
     *
     * @param libraryId 서재 ID
     * @param mapperDto 서재-책-매퍼 dto(서재에 책 넣은 정보)
     * @param memberId  사용자 ID
     */
    @Override
    public void addBookToLibrary(
        Long libraryId,
        RequestLibraryBookMapperCreateDto mapperDto,
        Long memberId
    ) {
        // 0. 서재 존재 확인
        Library library = findLibraryByIdWithException(libraryId);

        // 1. 해당 책이 DB에 있는지 확인 후 없으면 생성하는 api 요청하기
        ResponseBookDto bookResponse = bookServiceClient.getOrCreateBookByBookData(
            mapperDto.bookDto());

        // 2. 해당 책에 대한 id 값을 받아서 책-서재 매퍼에 추가해 책 등록하기
        // 2.1 mapper key 생성
        MapperKey mapperKey = new MapperKey();
        mapperKey.setLibraryId(libraryId);
        mapperKey.setBookId(bookResponse.id());

        // 2.1.1 이미 존재하는 매퍼가 있는지 확인
        if (libraryBookMapperRepository.existsById(mapperKey)) {
            throw new BookAlreadyMappedException();
        }

        // 2.2 엔티티 생성
        LibraryBookMapper lbMapper = libraryBookMapperMapper.toEntity(mapperDto, mapperKey);
        // 2.3 가장 큰 BookOrder 값 + 1을 넣어주기
        int maxBookOrder = libraryBookMapperRepository.findMaxBookOrderByLibraryId(libraryId);
        lbMapper.setBookOrder(
            maxBookOrder + 1);
        // lbMapper와 서재 매핑해주기
        lbMapper.setLibrary(library);
        // 2.3 매퍼 엔티티 저장
        libraryBookMapperRepository.save(lbMapper);

    }

    /**
     * 서재에 책 몇권있는지 세기
     *
     * @param memberId : 사용자 ID
     * @return 권수
     */
    @Override
    @Transactional(readOnly = true)
    public Integer countBooksInLibrary(Long memberId) {
        return libraryBookMapperRepository.countLibrariesByMemberId(memberId);
    }

    @Override
    public List<ResponseBookDto> getMyBooks(
        Long memberId,
        RequestSearchBookMultiFieldDto searchDto
    ) {
        // 라이브러리 ID로 연결된 책 ID 목록을 가져옵니다.
        List<Long> bookIds = libraryBookMapperRepository.findBookIdsByMemberId(memberId);
        // 없을 경우
        if (bookIds.isEmpty()) {
            return List.of();
        }
        // 책 ID 목록을 String 목록으로 변환합니다.
        List<String> stringBookIds = bookIds.stream()
                                            .map(String::valueOf)
                                            .toList();

        List<SearchBookConditionDto> conditions = new ArrayList<>();
        // BookServiceClient를 통해 책 정보를 가져옵니다.
        // 1. bookId로 condition 생성
        conditions.add(SearchBookConditionDto.builder()
                                             .field("id")
                                             .values(stringBookIds)
                                             .build());
        // 2. 제목/출판사/저자로 필터링하는 condition 생성
        conditions.addAll(searchDto.conditions());

        // 필터 DTO를 생성합니다.
        RequestSearchBookMultiFieldDto filterDto = RequestSearchBookMultiFieldDto.builder()
                                                                                 .conditions(
                                                                                     conditions)
                                                                                 .limit(
                                                                                     searchDto.limit()) // 이건 바꿔야할듯
                                                                                 .offset(
                                                                                     searchDto.offset())
                                                                                 .build();

        // 책 정보 가져오기
        List<ResponseBookDto> books = bookServiceClient.getBooksByCondition(filterDto);

        return books;
    }

    @Override
    @Transactional
    public Boolean updateLibraryBookMappers(
        Long libraryId,
        List<RequestLibraryBookMapperUpdateDto> dtos,
        Long memberId
    ) {
        for (RequestLibraryBookMapperUpdateDto dto : dtos) {
            MapperKey id = new MapperKey();
            id.setLibraryId(libraryId);
            id.setBookId(dto.bookId());

            LibraryBookMapper lbMapper = libraryBookMapperRepository.findById(id)
                                                                    .orElseThrow(
                                                                        () -> new IllegalArgumentException(
                                                                            "Invalid book ID or library ID"));

            libraryBookMapperMapper.updateLibraryBookMapperFromDto(dto, lbMapper);
            libraryBookMapperRepository.save(lbMapper);
        }
        return null;
    }

    /**
     * 내 서재 목록 불러오는 메서드
     *
     * @param memberId : member Id
     * @return : List ResponseLibraryDto
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseLibraryDto> getMyLibraries(Long memberId) {
        return libraryMapper.toResponseDtoList(
            libraryRepository.findByMemberId(memberId));
    }

    /**
     * 사용자의 서재에 여러 책이 등록되어 있는지 여부를 확인하는 API
     *
     * @param memberId member ID
     * @param bookIds  bookId 리스트
     * @return Map<BookId, boolean>
     */
    @Override
    public Map<Long, Boolean> areBooksInLibrary(
        Long memberId,
        List<Long> bookIds
    ) {
        List<Long> booksInLibrary = libraryBookMapperRepository.findBookIdsByMemberIdAndBookIds(
            memberId, bookIds);
        return bookIds.stream()
                      .collect(Collectors.toMap(bookId -> bookId, booksInLibrary::contains));
    }

    /**
     * 사용자 서재 내 책 단일 조회
     *
     * @param headers   토큰을 위한 헤더
     * @param libraryId 서재 id
     * @param bookId    book id
     * @return ResponseBookOfLibraryDto
     */
    @Override
    public ResponseLibraryBookDto getBookOfLibrary(
        HttpHeaders headers,
        Long libraryId,
        Long bookId
    ) {
        // book 정보 가져오기
        ResponseBookOfLibraryDto book = bookServiceClient.getBookOfLibrary(headers, bookId);

        // mapper key
        MapperKey key = new MapperKey();
        key.setLibraryId(libraryId);
        key.setBookId(bookId);

        // library Book Mapper 가져오기
        Optional<LibraryBookMapper> libraryBookMapper = libraryBookMapperRepository.findById(key);
        if (libraryBookMapper.isEmpty()) {
            throw new LibraryBookNotFoundException(
                "(libraryId, bookId) not found: (" + libraryId + " , " + bookId + ")");
        }
        LibraryBookMapper lbMapper = libraryBookMapper.get();

        // ResponseLibraryBookDto 생성 및 반환
        return libraryBookMapperMapper.entityBooktoDto(lbMapper, book);
    }

    /**
     * 서재 삭제 : libraryBookMapper 도 cascade 삭제 필요
     *
     * @param memberId  사용자 ID
     * @param libraryId 서재 ID
     * @return true / false
     */
    @Override
    public Boolean deleteLibrary(Long memberId, Long libraryId) {
        Optional<Library> libraryOpt = libraryRepository.findById(libraryId);

        // 서재가 없을 때
        if (libraryOpt.isEmpty()) {
            throw new LibraryNotFoundException(libraryId);
        }

        // 서재가 내게 아닐 때
        if (!isLibraryOwnedByUser(libraryOpt.get(), memberId)) {
            throw new LibraryIsNotYoursException();
        }

        // 서재가 존재하면서, 소유자가 확실하다면, 삭제
        try {
            libraryRepository.deleteById(libraryId);
            return true;
        } catch (Exception e) {
            // 삭제 중 에러 발생
            return false;
        }

    }

    /**
     * 해당 서재가 해당 사용자의 서재인지 확인
     *
     * @param library  서재 ID
     * @param memberId 사용자 ID
     * @return true/false
     */
    private boolean isLibraryOwnedByUser(Library library, Long memberId) {
        return library.getMemberId()
                      .equals(memberId);
    }

    /**
     * 서재 ID로 서재 찾기 with Exception
     *
     * @param libraryId 서재 ID
     * @return Library 엔티티
     */
    private Library findLibraryByIdWithException(Long libraryId) {
        return libraryRepository.findById(libraryId)
                                .orElseThrow(() -> new LibraryNotFoundException(
                                    libraryId));
    }
}
