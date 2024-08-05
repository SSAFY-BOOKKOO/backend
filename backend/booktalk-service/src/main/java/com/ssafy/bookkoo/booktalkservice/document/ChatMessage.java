package com.ssafy.bookkoo.booktalkservice.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chatMessages")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    private String id;
    private Long sender;
    private String profileImgUrl;
    private String message;
    private Long bookTalkId;
    private Long like;

    @Builder

    public ChatMessage(Long sender, String profileImgUrl, String message, Long bookTalkId,
        Long like) {
        this.sender = sender;
        this.profileImgUrl = profileImgUrl;
        this.message = message;
        this.bookTalkId = bookTalkId;
        this.like = like;
    }
}
