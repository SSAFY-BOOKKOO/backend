package com.ssafy.bookkoo.booktalkservice.mongo;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    /**
     * 해당 독서록의 채팅을 최근 순으로 Paging 반환
     *
     * @param bookTalkId 독서록 번호
     * @param pageable   페이징 번호
     * @return 최근 채팅 기록
     */
    List<ChatMessage> findByBookTalkIdOrderByCreatedAtDesc(Long bookTalkId, Pageable pageable);
    
}

