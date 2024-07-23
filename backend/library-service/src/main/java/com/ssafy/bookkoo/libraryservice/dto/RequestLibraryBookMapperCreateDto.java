package com.ssafy.bookkoo.libraryservice.dto;

import com.ssafy.bookkoo.libraryservice.entity.Status;
import jakarta.validation.constraints.NotNull;
import java.sql.Date;

public record RequestLibraryBookMapperCreateDto(
    @NotNull(message = "책 순서를 입력해주세요")
    Integer bookOrder,
    String bookColor,
    Date startAt,
    Date endAt,
    Status status,
    Integer rating,
    RequestBookDto bookDto
) {

}
