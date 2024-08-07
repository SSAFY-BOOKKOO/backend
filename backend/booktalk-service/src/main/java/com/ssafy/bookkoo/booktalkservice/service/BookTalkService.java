package com.ssafy.bookkoo.booktalkservice.service;


import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import java.util.List;

public interface BookTalkService {

    // BookTalk 생성
    void createBookTalk(RequestCreateBookTalkDto dto);

    // BookTalk 참여
    void enterBookTalk(Long bookTalkId, Long memberId);

    // 내 북톡 리스트 주기
    List<ResponseBookTalkDto> getMyBookTalkList(Long memberId, String order);

    // 가장 인기 있는 북톡 리스트 주기
    List<ResponseBookTalkDto> getPopularBookTalkList(Long memberId);

    //TODO 검색창에서 BookTalk 찾기 (해당 독서록이 있는지)
    ResponseBookTalkDto findBookTalk(Long bookId);
}
