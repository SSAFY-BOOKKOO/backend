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

    /**
     * RequestUpdateLibraryDto로부터 Library의 LibraryStyle 엔티티를 업데이트합니다. LibraryStyle 엔티티가 null인 경우 새로운
     * LibraryStyle 엔티티를 생성합니다. libraryColor 필드가 null이 아닌 경우에만 업데이트합니다.
     *
     * @param dto    업데이트 데이터가 포함된 DTO
     * @param entity 업데이트할 Library 엔티티
     */
    @AfterMapping
    default void updateLibraryStyleFromDto(
        RequestUpdateLibraryDto dto,
        @MappingTarget Library entity
    ) {
        if (dto.libraryStyleDto() != null && dto.libraryStyleDto()
                                                .libraryColor() != null) {
            if (entity.getLibraryStyle() == null) {
                entity.setLibraryStyle(LibraryStyle.builder()
                                                   .build());
            }
            entity.getLibraryStyle()
                  .setLibraryColor(dto.libraryStyleDto()
                                      .libraryColor());
        }
    }
}
