package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ChatService {

    void sendMessage(ChatMessage message);

    List<ChatMessage> getMessageList(Long bookTalkId, Pageable pageable);

}
