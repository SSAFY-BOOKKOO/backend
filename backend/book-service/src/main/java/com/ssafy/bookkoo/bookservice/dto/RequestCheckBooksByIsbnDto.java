package com.ssafy.bookkoo.bookservice.dto;

import jakarta.validation.constraints.NotNull;

public record RequestCheckBooksByIsbnDto(
    @NotNull
    String[] isbnList
) {
    
}
