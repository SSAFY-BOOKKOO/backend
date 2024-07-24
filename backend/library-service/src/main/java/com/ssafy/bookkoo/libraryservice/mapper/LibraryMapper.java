package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.LibraryStyleDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.entity.LibraryStyle;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LibraryMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "libraryOrder", source = "dto.libraryOrder")
    @Mapping(target = "id", ignore = true)
    void updateLibraryFromDto(
        RequestUpdateLibraryDto dto,
        @MappingTarget Library entity
    );

    // DTO -> entity
    @Mapping(target = "memberId", ignore = true)
    Library toEntity(RequestCreateLibraryDto dto);

    default Library toEntity(RequestCreateLibraryDto dto, Long memberId) {
        Library library = toEntity(dto);
        library.setMemberId(memberId);
        return library;
    }

    // entity -> DTO
    @Mapping(target = "libraryStyleDto", source = "libraryStyle")
    ResponseLibraryDto toResponseDto(Library library);

    LibraryStyleDto toLibraryStyleDto(LibraryStyle libraryStyle);

    // list<entity> -> list<dto>
    List<ResponseLibraryDto> toResponseDtoList(List<Library> libraries);
}
