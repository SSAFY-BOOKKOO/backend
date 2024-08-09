package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.client.MemberServiceClient;
import com.ssafy.bookkoo.booktalkservice.document.ChatMessage;
import com.ssafy.bookkoo.booktalkservice.dto.RequestChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseChatMessageDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.exception.ChatMessageNotFoundException;
import com.ssafy.bookkoo.booktalkservice.mongo.ChatMessageRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessageSendingOperations simpleMessageSendingOperations;
    private final MemberServiceClient memberServiceClient;
    private final BookTalkService bookTalkService;

    // 메세지 전송
    @Override
    @Transactional
    public void sendMessage(RequestChatMessageDto dto, Long memberId, Long bookTalkId) {
        BookTalk bookTalk = bookTalkService.findBookTalk(bookTalkId);
        ChatMessage chatMessage = ChatMessage.builder()
                                             .message(dto.content())
                                             .sender(memberId)
                                             .bookTalkId(bookTalkId)
                                             .build();
        // 채팅 MongoDB 에 저장
        chatMessageRepository.save(chatMessage);
        // 작성자 정보 가져오기
        ResponseMemberInfoDto memberInfo = memberServiceClient.getMemberInfoById(memberId);
        ResponseChatMessageDto responseDto
            = ResponseChatMessageDto.builder()
                                    .message(chatMessage.getMessage())
                                    .messageId(chatMessage.getId())
                                    .bookTalkId(chatMessage.getBookTalkId())
                                    .like(chatMessage.getLike())
                                    .nickName(memberInfo.nickName())
                                    .profileImgUrl(memberInfo.profileImgUrl())
                                    .createdAt(chatMessage.getCreatedAt()
                                                          .toString())
                                    // 보낼때는 좋아요를 누를 수 없다.
                                    .isMemberLiked(false)
                                    .build();
        // 해당 주소를 구독하고 있는 사람들에게 보냄
        simpleMessageSendingOperations.convertAndSend("/booktalks/sub/chat/" + bookTalkId,
            responseDto
        );
        // 해당 독서록의 채팅 카운트를 올린다.
        bookTalk.chatCounting();
    }

    @Override
    public List<ResponseChatMessageDto> getMessageList(Long bookTalkId, LocalDateTime time,
        Long memberId) {
        if (time == null) {
            time = LocalDateTime.now();
        }
        List<ChatMessage> chatMessageList = chatMessageRepository.findTop10ByBookTalkIdAndCreatedAtBeforeOrderByCreatedAtDesc(
            bookTalkId, time);
        Deque<ResponseChatMessageDto> messageList = new ArrayDeque<>();
        chatMessageList.forEach(chatMessage -> {
            ResponseMemberInfoDto memberInfo = memberServiceClient.getMemberInfoById(
                chatMessage.getSender());
            ResponseChatMessageDto dto
                = ResponseChatMessageDto.builder()
                                        .memberId(memberInfo.memberId())
                                        .message(chatMessage.getMessage())
                                        .messageId(chatMessage.getId())
                                        .bookTalkId(chatMessage.getBookTalkId())
                                        .like(chatMessage.getLike())
                                        .nickName(memberInfo.nickName())
                                        .profileImgUrl(memberInfo.profileImgUrl())
                                        .createdAt(chatMessage.getCreatedAt()
                                                              .toString())
                                        .isMemberLiked(chatMessage.isLiked(memberId))
                                        .build();
            messageList.addFirst(dto);
        });
        return messageList.stream()
                          .toList();
    }

    @Transactional
    @Override
    public Boolean chatMessageLikeToggle(String chatMessageId, Long memberId) {
        ChatMessage chatMessage
            = chatMessageRepository.findById(chatMessageId)
                                   .orElseThrow(
                                       ChatMessageNotFoundException::new);
        Boolean status = chatMessage.toggleLike(memberId);
        chatMessageRepository.save(chatMessage);
        return status;
    }
}
