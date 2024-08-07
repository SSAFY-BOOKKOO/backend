package com.ssafy.bookkoo.booktalkservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 독서록 Entity
 * <p>
 * 채팅이 작성될때마다 MessageCount 를 올려준다. 매일 dayCount 는 0으로 초기화한다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookTalk extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long book;

    @Column
    private Long dayMessageCount;

    @Column
    private Long totalMessageCount;

    @Builder
    public BookTalk(Long book) {
        this.book = book;
        this.dayMessageCount = 0L;
        this.totalMessageCount = 0L;
    }

    public void chatCounting() {
        dayMessageCount++;
        totalMessageCount++;
    }
}
