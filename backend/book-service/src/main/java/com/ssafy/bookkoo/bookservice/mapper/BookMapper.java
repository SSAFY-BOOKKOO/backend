package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BookMapper {

    // DTO를 엔티티로 변환
    Book toEntity(RequestCreateBookDto dto);

    // 엔티티를 DTO로 변환
    @Mapping(source = "category", target = "category")
    ResponseBookDto toResponseDto(Book book);

    // 리스트 변환
    List<ResponseBookDto> toResponseDtoList(List<Book> books);
}
