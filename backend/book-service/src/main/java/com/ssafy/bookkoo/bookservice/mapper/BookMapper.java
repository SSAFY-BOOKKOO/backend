package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.AladinBookItem;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.OriginAladinBookItem;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinDetail;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseOriginAladinAPI;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseOriginAladinDetail;
import com.ssafy.bookkoo.bookservice.util.AladinAPI.ResponseOriginAladinSearchDetail;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface BookMapper {

    // DTO를 엔티티로 변환
    Book toEntity(RequestCreateBookDto dto);

    @Mapping(source = "coverImgUrl", target = "coverImgUrl")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "publisher", target = "publisher")
    @Mapping(source = "description", target = "summary")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "subInfo.itemPage", target = "itemPage")
    @Mapping(source = "subInfo.packing.sizeDepth", target = "sizeDepth")
    @Mapping(source = "subInfo.packing.sizeHeight", target = "sizeHeight")
    @Mapping(source = "subInfo.packing.sizeWidth", target = "sizeWidth")
    Book toEntity(ResponseAladinSearchDetail dto);

    // 엔티티를 DTO로 변환
    @Mapping(source = "category", target = "category")
    ResponseBookDto toResponseDto(Book book);

    // OriginAladinBookItem을 AladinBookItem으로 변환
    @Mappings({
        @Mapping(source = "cover", target = "coverImgUrl"),
        @Mapping(source = "author", target = "author"),
        @Mapping(source = "publisher", target = "publisher"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "isbn", target = "isbn")
    })
    AladinBookItem toAladinBookItem(OriginAladinBookItem dto);

    // ResponseOriginAladinAPI를 ResponseAladinAPI로 변환
    @Mappings({
        @Mapping(source = "totalResults", target = "totalResults"),
        @Mapping(source = "startIndex", target = "startIndex"),
        @Mapping(source = "item", target = "item"),
        @Mapping(source = "itemsPerPage", target = "itemsPerPage")
    })
    ResponseAladinAPI toResponseAladinAPI(ResponseOriginAladinAPI dto);


    @Mapping(source = "cover", target = "coverImgUrl")
    ResponseAladinSearchDetail toResponseSearchDetail(ResponseOriginAladinSearchDetail dto);

    ResponseAladinDetail toResopnseAladinDetail(ResponseOriginAladinDetail dto);

    // 리스트 변환
    List<ResponseBookDto> toResponseDtoList(List<Book> books);
}
