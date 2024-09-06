package com.ssafy.bookkoo.memberservice.mapper;


import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDetailDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDto;
import com.ssafy.bookkoo.memberservice.entity.Quote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberInfo", ignore = true)
    @Mapping(target = "backgroundImgUrl", ignore = true)
    Quote toEntity(RequestCreateQuoteDto createQuoteDto);

    @Mapping(target = "quoteId", source = "id")
    ResponseQuoteDto toDto(Quote quote);

    @Mapping(target = "quoteId", source = "id")
    ResponseQuoteDetailDto toDetailDto(Quote quote);
}
