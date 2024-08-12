package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.library.LibraryStyleDto;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestCreateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.RequestUpdateLibraryDto;
import com.ssafy.bookkoo.libraryservice.dto.library.ResponseLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.Library;
import com.ssafy.bookkoo.libraryservice.entity.LibraryStyle;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Library 엔티티와 다양한 DTO 간의 변환을 처리하는 매퍼 인터페이스. MapStruct를 사용하여 구현을 생성합니다.
 */
@Mapper(componentModel = "spring")
public interface LibraryMapper {

    /**
     * RequestUpdateLibraryDto로부터 기존 Library 엔티티를 업데이트합니다. DTO의 null 값은 업데이트 시 무시됩니다.
     *
     * @param dto    업데이트 데이터가 포함된 DTO
     * @param entity 업데이트할 Library 엔티티
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "libraryOrder", source = "dto.libraryOrder")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberId", ignore = true)
    @Mapping(target = "libraryStyle", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateLibraryFromDto(
        RequestUpdateLibraryDto dto,
        @MappingTarget Library entity
    );

    /**
     * RequestCreateLibraryDto를 Library 엔티티로 변환합니다. 변환 시 memberId 필드는 무시됩니다.
     *
     * @param dto 변환할 DTO
     * @return 변환된 Library 엔티티
     */
    @Mapping(target = "memberId", ignore = true)
    Library toEntity(RequestCreateLibraryDto dto);

    /**
     * RequestCreateLibraryDto를 Library 엔티티로 변환하고 memberId를 설정합니다.
     *
     * @param dto      변환할 DTO
     * @param memberId Library 엔티티에 설정할 memberId
     * @return memberId가 설정된 Library 엔티티
     */
    default Library toEntity(
        RequestCreateLibraryDto dto,
        Long memberId
    ) {
        Library library = toEntity(dto);
        library.setMemberId(memberId);
        return library;
    }

    /**
     * Library 엔티티를 ResponseLibraryDto로 변환합니다. LibraryStyle 엔티티는 LibraryStyleDto로 매핑됩니다.
     *
     * @param library 변환할 Library 엔티티
     * @return 변환된 ResponseLibraryDto
     */
    @Mapping(target = "libraryStyleDto", source = "libraryStyle")
    @Mapping(target = "bookCount", expression = "java(library.getBookCount())")
    ResponseLibraryDto toResponseDto(Library library);

    /**
     * LibraryStyle 엔티티를 LibraryStyleDto로 변환합니다.
     *
     * @param libraryStyle 변환할 LibraryStyle 엔티티
     * @return 변환된 LibraryStyleDto
     */
    LibraryStyleDto toLibraryStyleDto(LibraryStyle libraryStyle);

    /**
     * Library 엔티티 목록을 ResponseLibraryDto 목록으로 변환합니다.
     *
     * @param libraries 변환할 Library 엔티티 목록
     * @return 변환된 ResponseLibraryDto 목록
     */
    @Mapping(target = "libraryStyleDto", source = "libraryStyle")
    @Mapping(target = "bookCount", expression = "java(library.getBookCount())")
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
        if (dto.libraryStyleDto() != null) {
            if (entity.getLibraryStyle() == null) {
                entity.setLibraryStyle(LibraryStyle.builder()
                                                   .build());
            }
            if (dto.libraryStyleDto()
                   .libraryColor() != null) {
                entity.getLibraryStyle()
                      .setLibraryColor(dto.libraryStyleDto()
                                          .libraryColor());
            }
            if (dto.libraryStyleDto()
                   .fontName() != null) {
                entity.getLibraryStyle()
                      .setFontName(dto.libraryStyleDto()
                                      .fontName());
            }
            if (dto.libraryStyleDto()
                   .fontSize() != null) {
                entity.getLibraryStyle()
                      .setFontSize(dto.libraryStyleDto()
                                      .fontSize());
            }
        }
    }
}
