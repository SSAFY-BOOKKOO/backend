package com.ssafy.bookkoo.booktalkservice.mongo;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findByBookTalkId(Long bookTalkId, Pageable pageable);
}

