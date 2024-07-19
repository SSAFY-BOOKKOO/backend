package com.ssafy.bookkoo.bookservice.dto;

public record RequestSearchBookFilterDto(
    String type,
    String content,
    Integer offset,
    Integer limit
) {

    public RequestSearchBookFilterDto {
        // 기본값
        if (offset == null) {
            offset = 0;
        }
        if (limit == null || limit <= 0) {
            limit = 10;
        }
    }
}
