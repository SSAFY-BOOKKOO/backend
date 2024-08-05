package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;

public interface ChatService {

    void sendMessage(ChatMessage message);

}
