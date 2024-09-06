package com.ssafy.bookkoo.bookservice.dto.aladin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ssafy.bookkoo.bookservice.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * 알라딘 API로부터 받아오는 상세 검색 결과 정보를 담는 DTO 클래스입니다.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "알라딘 API로부터 받아오는 상세 검색 결과 정보를 담는 DTO")
public class ResponseAladinSearchDetail {

    @Schema(description = "저자", example = "J.K. Rowling")
    private String author;

    @Schema(description = "ISBN", example = "978-0439139601")
    private String isbn;

    @Schema(description = "책 요약", example = "This is a summary of the book.")
    private String summary;

    @Schema(description = "책 제목", example = "Harry Potter and the Goblet of Fire")
    private String title;

    @Schema(description = "출판일", example = "2000-07-08")
    private String publishedAt;

    @Schema(description = "표지 이미지 URL", example = "http://example.com/cover.jpg")
    private String coverImgUrl;

    @Schema(description = "출판사", example = "Bloomsbury Publishing")
    private String publisher;

    @Schema(description = "카테고리 ID", example = "123")
    private Integer categoryId;

    @Schema(description = "카테고리")
    private Category category;

    @Schema(description = "포장 깊이", example = "10")
    private Integer sizeDepth;

    @Schema(description = "포장 높이", example = "20")
    private Integer sizeHeight;

    @Schema(description = "포장 너비", example = "15")
    private Integer sizeWidth;

    @Schema(description = "아이템 페이지 수", example = "350")
    private Integer itemPage;
}
