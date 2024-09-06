package com.ssafy.bookkoo.bookservice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class DateMapper {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Named("asDate")
    public LocalDate asDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDate.parse(date, formatter);
    }

    @Named("asString")
    public String asString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }
}
