package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.dto.library_book.ResponseLibraryBookDto;
import com.ssafy.bookkoo.libraryservice.dto.other.ResponseBookOfLibraryDto;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.MapperKey;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * LibraryBookMapper 엔티티와 DTO 간의 매핑을 처리하는 인터페이스입니다.
 */
@Mapper(componentModel = "spring")
public interface LibraryBookMapperMapper {

    /**
     * RequestLibraryBookMapperUpdateDto를 사용하여 LibraryBookMapper 엔티티를 업데이트합니다. null 값이 아닌 속성만
     * 업데이트합니다.
     *
     * @param dto    업데이트할 DTO
     * @param entity 업데이트할 엔티티
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "bookColor", source = "dto.bookColor")
    @Mapping(target = "bookOrder", source = "dto.bookOrder")
    @Mapping(target = "startAt", source = "dto.startAt")
    @Mapping(target = "endAt", source = "dto.endAt")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "rating", source = "dto.rating")
    void updateLibraryBookMapperFromDto(
        RequestLibraryBookMapperUpdateDto dto,
        @MappingTarget LibraryBookMapper entity
    );

    @Mapping(source = "entity.bookOrder", target = "bookOrder")
    @Mapping(source = "entity.bookColor", target = "bookColor")
    @Mapping(source = "entity.startAt", target = "startAt")
    @Mapping(source = "entity.endAt", target = "endAt")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.rating", target = "rating")
    @Mapping(source = "book", target = "book")
    ResponseLibraryBookDto entityBooktoDto(LibraryBookMapper entity, ResponseBookOfLibraryDto book);

    /**
     * RequestLibraryBookMapperCreateDto와 MapperKey를 사용하여 LibraryBookMapper 엔티티를 생성합니다.
     *
     * @param dto       생성할 DTO
     * @param mapperKey 매퍼 키
     * @return 생성된 LibraryBookMapper 엔티티
     */
    default LibraryBookMapper toEntity(RequestLibraryBookMapperCreateDto dto, MapperKey mapperKey) {
        return LibraryBookMapper.builder()
                                .id(mapperKey)
                                .bookColor(dto.bookColor())
                                .startAt(dto.startAt())
                                .endAt(dto.endAt())
                                .status(dto.status())
                                .rating(dto.rating())
                                .build();
    }
}
