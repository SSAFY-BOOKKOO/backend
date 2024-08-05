package com.ssafy.bookkoo.booktalkservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookTalkMemberMapper extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "booktalkId")
    private BookTalk booktalkId;


    @Builder
    public BookTalkMemberMapper(Long memberId, BookTalk booktalkId) {
        this.memberId = memberId;
        this.booktalkId = booktalkId;
    }
}

