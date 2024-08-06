package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import com.ssafy.bookkoo.booktalkservice.mongo.ChatMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    final private ChatMessageRepository chatMessageRepository;

    @Override
    public void sendMessage(ChatMessage message) {
        //TODO 구독자들에게 메세지 보내기
        chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessageList(Long bookTalkId, Pageable pageable) {
        return chatMessageRepository.findByBookTalkId(bookTalkId, pageable);
    }
}
