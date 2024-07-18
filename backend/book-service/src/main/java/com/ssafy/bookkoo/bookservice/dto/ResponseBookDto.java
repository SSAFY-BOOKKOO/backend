package com.ssafy.bookkoo.bookservice.dto;

import com.ssafy.bookkoo.bookservice.entity.Book;
import lombok.Builder;

@Builder
public record ResponseBookDto(
    Long id,
    String coverImgUrl,
    String author,
    String publisher,
    String summary,
    String title
) {

    public Book toBook() {
        Book book = Book.builder()
                        .coverImgUrl(this.coverImgUrl)
                        .author(this.author)
                        .publisher(this.publisher)
                        .summary(this.summary)
                        .title(this.title)
                        .build();
        book.setId(this.id);
        return book;
    }
}
