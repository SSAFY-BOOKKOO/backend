package com.ssafy.bookkoo.bookservice.dto.category;

import java.util.List;
import lombok.Builder;

@Builder
public record CategorySearchParam(
    String field,
    List<String> values
) {

}
