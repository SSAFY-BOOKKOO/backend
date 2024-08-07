package com.ssafy.bookkoo.booktalkservice.document;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 채팅 Message Document
 * <p>
 * MongoDB 안에 저장된다.
 */
@Document(collection = "chatMessages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    private String id;

    private Long sender;

    private String message;

    private Long bookTalkId;

    private Long like;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public ChatMessage(Long sender, String message, Long bookTalkId
    ) {
        this.sender = sender;
        this.message = message;
        this.bookTalkId = bookTalkId;
        this.like = 0L;
    }
}
