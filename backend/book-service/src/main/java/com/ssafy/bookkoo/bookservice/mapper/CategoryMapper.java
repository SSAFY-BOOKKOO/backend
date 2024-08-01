package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.category.CategoryDto;
import com.ssafy.bookkoo.bookservice.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Category 엔티티와 DTO 간의 변환을 처리하는 매퍼 인터페이스입니다.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * CategoryDto를 Category 엔티티로 변환합니다.
     *
     * @param categoryDto CategoryDto 객체
     * @return 변환된 Category 엔티티
     */
    Category toEntity(CategoryDto categoryDto);

    /**
     * Category 엔티티를 CategoryDto로 변환합니다.
     *
     * @param category Category 엔티티
     * @return 변환된 CategoryDto
     */
    CategoryDto toDto(Category category);

    /**
     * Category 엔티티 리스트를 CategoryDto 리스트로 변환합니다.
     *
     * @param categories Category 엔티티 리스트
     * @return 변환된 CategoryDto 리스트
     */
    List<CategoryDto> toResponseDtoList(List<Category> categories);
}
