package com.ssafy.bookkoo.curationservice.dto;

/**
 * Curation 상세 조회 응답 DTO
 *
 * @author dino9881
 */

public record ResponseCurationDetailDto(String coverImgUrl, String curationTitle, String writer,
                                        String content, String createdAt,
                                        String bookTitle, String author, String summary) {

}
