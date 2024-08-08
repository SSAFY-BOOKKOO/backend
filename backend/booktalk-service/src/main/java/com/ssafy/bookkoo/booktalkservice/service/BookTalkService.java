package com.ssafy.bookkoo.booktalkservice.service;


import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookTalkService {


    /**
     * 독서록을 생성하는 메서드
     *
     * @param dto : 책 번호
     * @return 생성된 독서록 번호
     */
    Long createBookTalk(RequestCreateBookTalkDto dto);


    /**
     * 독서록에 참여하는 메서드
     *
     * @param bookTalkId : 독서록 번호
     * @param memberId   : 사용자 Passport
     */
    void enterBookTalk(Long bookTalkId, Long memberId);


    /**
     * 내가 참여하고 있는 독서록 리스트를 반환하는 메서드
     *
     * @param memberId : 사용자 Passport
     * @param order    : 정렬 기준 (time, chat)
     * @param pageable : 페이징 번호
     * @return 독서록 정보
     * <p>
     * 독서록 ID, 책 제목, 작가, 카테고리, 책 커버 이미지, 최근 채팅 시간, 채팅 수
     */
    List<ResponseBookTalkDto> getMyBookTalkList(Long memberId, String order, Pageable pageable);


    /**
     * 오늘 가장 채팅이 많은 독서록 리스트를 반환하는 메서드
     *
     * @param pageable : 페이징 번호
     * @return 독서록 정보
     * <p>
     * 독서록 ID, 책 제목, 작가, 카테고리, 책 커버 이미지, 최근 채팅 시간, 채팅 수
     */
    List<ResponseBookTalkDto> getPopularBookTalkList(Pageable pageable);


    /**
     * 책 번호로 독서록을 찾는 메서드
     *
     * @param bookId : 책 번호
     * @return 독서록 Entity
     * <p>
     * 채팅에서 독서록 Entity 의 counting 메서드를 사용해야 하기 때문에 Entity 를 반환
     */
    BookTalk findBookTalk(Long bookId);


    /**
     * 책 번호로 독서록의 정보를 가져오는 메서드
     *
     * @param bookId : 책 번호
     * @return 독서록 정보
     * <p>
     * 독서록 ID, 책 제목, 작가, 카테고리, 책 커버 이미지, 최근 채팅 시간, 채팅 수
     */
    ResponseBookTalkDto getBookTalkByBookId(Long bookId);

    /**
     * 북톡에 있는 책 반환
     *
     * @param searchDto 검색 조건
     * @return List(ResponseBookDto)
     */
    List<ResponseBookDto> searchBookTalkBooks(
        RequestSearchBookMultiFieldDto searchDto
    );

}
