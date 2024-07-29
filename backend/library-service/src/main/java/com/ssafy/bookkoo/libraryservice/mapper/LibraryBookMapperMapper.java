package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperUpdateDto;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.MapperKey;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LibraryBookMapperMapper {

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

    default LibraryBookMapper toEntity(RequestLibraryBookMapperCreateDto dto, MapperKey mapperKey) {
        return LibraryBookMapper.builder()
                                .id(mapperKey)
                                .bookOrder(dto.bookOrder())
                                .bookColor(dto.bookColor())
                                .startAt(dto.startAt())
                                .endAt(dto.endAt())
                                .status(dto.status())
                                .rating(dto.rating())
                                .build();
    }
}
