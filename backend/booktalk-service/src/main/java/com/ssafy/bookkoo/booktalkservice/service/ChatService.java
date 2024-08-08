package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseChatMessageDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

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
     * 해당 독서록의 이전 채팅 기록
     *
     * @param bookTalkId : 독서록 번호
     * @param pageable   : 페이지 번호
     * @return 채팅 기록
     * <p>
     * 메세지 번호, 독서록 번호, 작성자 닉네임, 작성자 프로필 이미지, 메세지 내용, 좋아요 수, 작성 시간
     */
    List<ResponseChatMessageDto> getMessageList(Long bookTalkId, Pageable pageable);

}
