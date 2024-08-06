package com.ssafy.bookkoo.booktalkservice.controller;

import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("Received a new web socket connection with {}", event.getUser());
    }

    @MessageMapping("/chat/{booktalkId}")
    public void sendMessage(@DestinationVariable(value = "booktalkId") Long booktalkId,
        Message<RequestChatMessageDto> message) {
        log.info("Sending message  id : {} msg : {}", booktalkId, message.getPayload()
                                                                         .content());
        simpleMessageSendingOperations.convertAndSend("/booktalks/sub/chat/" + booktalkId,
            message.getPayload()
                   .content());
    }


}
