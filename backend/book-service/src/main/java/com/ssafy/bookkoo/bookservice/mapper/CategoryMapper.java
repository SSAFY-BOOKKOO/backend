package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.CategoryDto;
import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

    // 리스트 변환
    List<CategoryDto> toResponseDtoList(List<Category> books);
}
