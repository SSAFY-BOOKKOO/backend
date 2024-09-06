package com.ssafy.bookkoo.bookservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book", indexes = {
    @Index(name = "idx_isbn", columnList = "isbn") // index 추가(쿼리 성능 위해)
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    @Setter
    private Long id;

    @Column(name = "cover_img_url")
    private String coverImgUrl;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "summary")
    private String summary;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column
    private Integer itemPage;

    @Column
    private Integer sizeDepth;

    @Column
    private Integer sizeHeight;

    @Column
    private Integer sizeWidth;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @Setter
    private Category category;

    @Column
    private LocalDate publishedAt;

    @Builder
    public Book(
        String coverImgUrl,
        String author,
        String publisher,
        String summary,
        String title,
        String isbn,
        Integer itemPage,
        Integer sizeDepth,
        Integer sizeHeight,
        Integer sizeWidth,
        Category category,
        LocalDate publishedAt
    ) {
        this.coverImgUrl = coverImgUrl;
        this.author = author;
        this.publisher = publisher;
        this.summary = summary;
        this.title = title;
        this.isbn = isbn;
        this.itemPage = itemPage;
        this.sizeDepth = sizeDepth;
        this.sizeHeight = sizeHeight;
        this.sizeWidth = sizeWidth;
        this.category = category;
        this.publishedAt = publishedAt;
    }
}
