package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import com.ssafy.bookkoo.booktalkservice.mongo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    final private ChatMessageRepository chatMessageRepository;

    @Override
    public void sendMessage(ChatMessage message) {
        //TODO 보내기
        chatMessageRepository.save(message);
    }


}
