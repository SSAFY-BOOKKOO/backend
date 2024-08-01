package com.ssafy.bookkoo.libraryservice.service;

import com.ssafy.bookkoo.libraryservice.dto.library.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.util.List;
import java.util.Map;

/**
 * 서재 관련 서비스 인터페이스입니다.
 */
public interface LibraryService {

    /**
     * 서재를 추가합니다.
     *
     * @param library  서재 생성 요청 DTO
     * @param memberId 멤버 ID
     * @return 생성된 서재 응답 DTO
     */
    ResponseLibraryDto addLibrary(RequestCreateLibraryDto library, Long memberId);

    /**
     * 특정 사용자의 서재 목록을 조회합니다.
     *
     * @param nickname 사용자의 닉네임
     * @return 서재 응답 DTO 리스트
     */
    List<ResponseLibraryDto> getLibrariesOfMember(String nickname);

    /**
     * 특정 서재를 조회합니다.
     *
     * @param libraryId 서재 ID
     * @param filter    상태 필터
     * @return 조회된 서재 응답 DTO
     */
    ResponseLibraryDto getLibrary(Long libraryId, Status filter);

    /**
     * 서재를 수정합니다.
     *
     * @param libraryId 서재 ID
     * @param library   서재 수정 요청 DTO
     * @return 수정된 서재 응답 DTO
     */
    ResponseLibraryDto updateLibrary(Long libraryId, RequestUpdateLibraryDto library);

    /**
     * 서재에 책을 추가합니다.
     *
     * @param libraryId            서재 ID
     * @param libraryBookMapperDto 서재 책 매핑 생성 요청 DTO
     * @param memberId             멤버 ID
     */
    void addBookToLibrary(
        Long libraryId,
        RequestLibraryBookMapperCreateDto libraryBookMapperDto,
        Long memberId
    );

    /**
     * 특정 멤버가 등록한 모든 책의 개수를 조회합니다.
     *
     * @param memberId 멤버 ID
     * @return 책 개수
     */
    Integer countBooksInLibrary(Long memberId);

    /**
     * 특정 멤버가 등록한 책을 조회합니다.
     *
     * @param memberId  멤버 ID
     * @param searchDto 책 검색 요청 DTO
     * @return 조회된 책 응답 DTO 리스트
     */
    List<ResponseBookDto> getMyBooks(Long memberId, RequestSearchBookMultiFieldDto searchDto);

    /**
     * 서재 내 책 매핑을 수정합니다.
     *
     * @param libraryId 서재 ID
     * @param lbmDto    서재 책 매핑 수정 요청 DTO 리스트
     * @param memberId  멤버 ID
     * @return 수정 성공 여부
     */
    Boolean updateLibraryBookMappers(
        Long libraryId,
        List<RequestLibraryBookMapperUpdateDto> lbmDto,
        Long memberId
    );

    /**
     * 내 서재 목록 불러오는 메서드
     *
     * @param memberId : member Id
     * @return : List ResponseLibraryDto
     */
    List<ResponseLibraryDto> getMyLibraries(Long memberId);

    /**
     * 사용자의 서재에 여러 책이 등록되어 있는지 여부를 확인하는 API
     *
     * @param memberId member ID
     * @param bookIds  bookId 리스트
     * @return Map<BookId, boolean>
     */
    Map<Long, Boolean> areBooksInLibrary(Long memberId, List<Long> bookIds);
}
