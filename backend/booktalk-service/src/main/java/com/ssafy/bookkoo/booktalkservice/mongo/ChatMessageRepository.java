package com.ssafy.bookkoo.booktalkservice.mongo;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    /**
     * 해당 독서록의 채팅을 기준시간 이전 10개 반환한다.
     *
     * @param bookTalkId 독서록 번호
     * @param time       기준 시간
     * @return 최근 채팅 기록
     */
    List<ChatMessage> findTop10ByBookTalkIdAndCreatedAtBeforeOrderByCreatedAtDesc(Long bookTalkId,
        LocalDateTime time);
}

