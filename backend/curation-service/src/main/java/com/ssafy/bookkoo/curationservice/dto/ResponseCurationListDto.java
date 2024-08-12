package com.ssafy.bookkoo.curationservice.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ResponseCurationListDto(
    List<ResponseCurationDto> curationList,
    Long count
) {


}
