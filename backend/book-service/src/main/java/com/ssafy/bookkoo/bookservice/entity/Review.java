package com.ssafy.bookkoo.bookservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column
    private String content;

    @Column
    private Integer rating;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<ReviewLike> likes = new ArrayList<>();

    @Builder
    public Review(Book book, Long memberId, String content, Integer rating) {
        this.book = book;
        this.memberId = memberId;
        this.content = content;
        this.rating = rating;
    }

    public Integer getLikeCount() {
        return likes.size();
    }
}
