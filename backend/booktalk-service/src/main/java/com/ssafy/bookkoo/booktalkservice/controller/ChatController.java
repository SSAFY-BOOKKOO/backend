package com.ssafy.bookkoo.booktalkservice.controller;

import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.service.ChatService;
import com.ssafy.bookkoo.booktalkservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/booktalks/chat")
public class ChatController {

    private final ChatService chatService;


    /**
     * 소켓 연결 이벤트 로깅하는 EventListener
     *
     * @param event 소켓 연결 이벤트
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        log.info("Received a new web socket connection with {}", event.getSource());
    }


    /**
     * 채팅 보내기 API
     *
     * <p>
     * <p>
     * sockJS 로 /booktalks/sub/chat/{bookTalkId}를 구독한 클라이언트에게 메세지를 보낸다.
     *
     * @param headers    사용자 passport
     * @param bookTalkId 해당 독서록
     * @param message    보낼 메세지 (content : string)
     */
    @PostMapping("/{bookTalkId}")
    @Operation(summary = "채팅 보내기",
        description = "/booktalks/sub/chat/{bookTalkId} 주소를 구독한 클라이언트에게 메세지를 보냅니다."
    )
    public void sendMessage(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "bookTalkId") Long bookTalkId,
        @RequestBody RequestChatMessageDto message) {
        log.info("Sending message  id : {} msg : {}", bookTalkId, message
            .content());
        Long memberId = CommonUtil.getMemberId(headers);
        chatService.sendMessage(message, memberId, bookTalkId);
    }

    /**
     * 독서록의 채팅 기록을 가져오는 API (페이징 10개씩)
     *
     * @param bookTalkId : 독서록 번호
     * @param page       : 페이징 번호
     * @return ResponseChatMessageDto 메세지 번호, 독서록 번호, 작성자 닉네임, 작성자 프로필, 메세지 내용, 좋아요 수 , 작성 시간
     */
    @Operation(summary = "채팅 기록 가져오기",
        description = "해당 독서록의 채팅을 최근순으로 10개씩 가져옵니다."
    )
    @GetMapping("/{bookTalkId}")
    public List<ResponseChatMessageDto> getMessageList(
        @PathVariable(value = "bookTalkId") Long bookTalkId,
        @RequestParam Integer page) {
        return chatService.getMessageList(bookTalkId, PageRequest.of(page, 10));
    }
}
