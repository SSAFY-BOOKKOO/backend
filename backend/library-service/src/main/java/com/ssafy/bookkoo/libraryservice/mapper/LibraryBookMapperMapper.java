package com.ssafy.bookkoo.libraryservice.mapper;

import com.ssafy.bookkoo.libraryservice.dto.RequestLibraryBookMapperCreateDto;
import com.ssafy.bookkoo.libraryservice.entity.LibraryBookMapper;
import com.ssafy.bookkoo.libraryservice.entity.MapperKey;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibraryBookMapperMapper {

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
