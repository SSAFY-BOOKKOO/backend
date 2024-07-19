package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LibraryMapper {

    LibraryMapper INSTANCE = Mappers.getMapper(LibraryMapper.class);

    // DTO -> entity
    Library toEntity(RequestCreateLibraryDto dto);

    // entity -> DTO
    ResponseLibraryDto toResponseDto(Library library);
    
    // list<entity> -> list<dto>
    List<ResponseLibraryDto> toResponseDtoList(List<Library> libraries);
}
