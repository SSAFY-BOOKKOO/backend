package com.ssafy.bookkoo.libraryservice.dto;

import com.ssafy.bookkoo.libraryservice.entity.Status;
import java.sql.Date;
import lombok.Builder;

@Builder
public record ResponseLibraryBookMapperDto(
    ResponseBookDto book,
    Integer bookOrder,
    String bookColor,
    Date startAt,
    Date endAt,
    Status status,
    Integer rating
) {

}
