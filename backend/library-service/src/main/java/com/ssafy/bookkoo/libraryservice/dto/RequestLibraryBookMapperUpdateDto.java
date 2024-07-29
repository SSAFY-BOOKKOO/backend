package com.ssafy.bookkoo.libraryservice.dto;

import com.ssafy.bookkoo.libraryservice.entity.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.sql.Date;

public record RequestLibraryBookMapperUpdateDto(
    Integer bookOrder,
    String bookColor,
    Date startAt,
    Date endAt,
    Status status,
    @Min(value = 1)
    @Max(value = 5)
    Integer rating,
    Long bookId
) {

}
