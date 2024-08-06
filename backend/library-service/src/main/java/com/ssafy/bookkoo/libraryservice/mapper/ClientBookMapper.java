package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseRecentFiveBookDto;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Book 서비스와의 통신으로 받은 것을 처리하는 매퍼 인터페이스
 */
@Mapper(componentModel = "spring")
public interface ClientBookMapper {

    List<ResponseRecentFiveBookDto> responseDtoToCustomDto(List<ResponseBookDto> dtoList);
}
