package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @Mapping(target = "id", ignore = true)
    void updateLibraryFromDto(
        RequestUpdateLibraryDto dto,
        @MappingTarget Library entity
    );

    // DTO -> entity
    Library toEntity(RequestCreateLibraryDto dto);

    // entity -> DTO
    ResponseLibraryDto toResponseDto(Library library);

    // list<entity> -> list<dto>
    List<ResponseLibraryDto> toResponseDtoList(List<Library> libraries);
}
