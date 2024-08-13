package com.ssafy.bookkoo.booktalkservice.controller;

import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.service.ChatService;
import com.ssafy.bookkoo.booktalkservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
        @Valid @RequestBody RequestChatMessageDto message) {
        log.info("Sending message  id : {} msg : {}", bookTalkId, message
            .content());
        Long memberId = CommonUtil.getMemberId(headers);
        chatService.sendMessage(message, memberId, bookTalkId);
    }

    /**
     * 독서록의 채팅 기록을 가져오는 API
     *
     * @param bookTalkId : 독서록 번호
     * @param time       : 기준 시간
     * @return ResponseChatMessageDto 메세지 번호, 독서록 번호, 작성자 닉네임, 작성자 프로필, 메세지 내용, 좋아요 수 , 작성 시간
     */
    @Operation(summary = "채팅 기록 가져오기",
        description = "해당 독서록의 채팅을 기준 시간 이전으로 10개씩 가져옵니다. time 을 주지 않을 경우 현재시간을 기준으로 가져옵니다."
    )
    @GetMapping("/{bookTalkId}")
    public List<ResponseChatMessageDto> getMessageList(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "bookTalkId") Long bookTalkId,
        @RequestParam(required = false) LocalDateTime time
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return chatService.getMessageList(bookTalkId, time, memberId);
    }

    /**
     * @param headers       : 멤버 Passport
     * @param chatMessageId : 좋아요를 토글할 채팅 메세지 아이디
     * @return : 좋아요 상태 변경 후 상태
     */
    @Operation(summary = "채팅 좋아요",
        description = "채팅 좋아요를 누른상태라면 취소, 누르지 않았다면 좋아요를 누릅니다. 변경된 상태를 반환합니다."
    )
    @PostMapping("/like/{chatMessageId}")
    public ResponseEntity<Boolean> checkMessageLike(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "chatMessageId") String chatMessageId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok(chatService.chatMessageLikeToggle(chatMessageId, memberId));
    }
}
