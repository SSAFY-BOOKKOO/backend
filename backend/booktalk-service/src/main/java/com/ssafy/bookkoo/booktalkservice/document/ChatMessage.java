package com.ssafy.bookkoo.booktalkservice.document;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
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

    private Set<Long> likes;

    @Indexed
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
        this.likes = new HashSet<>();
    }

    // 좋아요 추가 메서드
    private void addLike(Long memberId) {
        this.likes.add(memberId);
        this.like = (long) this.likes.size();
    }

    // 좋아요 제거 메서드
    private void removeLike(Long memberId) {
        this.likes.remove(memberId);
        this.like = (long) this.likes.size();
    }

    public Boolean toggleLike(Long memberId) {
        if (likes.contains(memberId)) {
            removeLike(memberId);
            return false;
        } else {
            addLike(memberId);
            return true;
        }
    }

    public Boolean isLiked(Long memberId) {
        return likes.contains(memberId);
    }

}
