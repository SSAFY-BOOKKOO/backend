package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseChatMessageDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    /**
     * 채팅 보내는 메서드
     *
     * @param dto        : {content : string}  보낼 메세지
     * @param memberId   : 사용자 Passport
     * @param bookTalkId : 독서록 번호
     */
    void sendMessage(RequestChatMessageDto dto, Long memberId, Long bookTalkId);

    /**
     * 해당 독서록의 기준시간 이전  10개의 채팅 기록
     *
     * @param bookTalkId : 독서록 번호
     * @param time       : 기준 시간
     * @return 채팅 기록
     * <p>
     * 메세지 번호, 독서록 번호, 작성자 닉네임, 작성자 프로필 이미지, 메세지 내용, 좋아요 수, 작성 시간
     */
    List<ResponseChatMessageDto> getMessageList(Long bookTalkId, LocalDateTime time, Long memberId);


    /**
     * 좋아요 상태를 토글하는 메서드
     *
     * @param chatMessageId : 해당 채팅 아이디
     * @param memberId      : 토글할 멤버
     * @return : 변화된 좋아요 상태 정보
     */
    Boolean chatMessageLikeToggle(String chatMessageId, Long memberId);

}
