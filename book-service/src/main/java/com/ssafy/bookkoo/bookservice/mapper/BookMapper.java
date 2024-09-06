package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.aladin.AladinBookItem;
import com.ssafy.bookkoo.bookservice.dto.aladin.OriginAladinBookItem;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinAPI;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinDetail;
import com.ssafy.bookkoo.bookservice.dto.aladin.ResponseOriginAladinSearchDetail;
import com.ssafy.bookkoo.bookservice.dto.book.RequestCreateBookDto;
import com.ssafy.bookkoo.bookservice.dto.book.ResponseBookDto;
import com.ssafy.bookkoo.bookservice.entity.Book;
import com.ssafy.bookkoo.bookservice.util.DateMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Book 엔티티와 DTO 간의 변환을 처리하는 매퍼 인터페이스입니다.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, DateMapper.class})
public interface BookMapper {

    /**
     * RequestCreateBookDto를 Book 엔티티로 변환합니다.
     *
     * @param dto RequestCreateBookDto 객체
     * @return 변환된 Book 엔티티
     */
    @Mapping(source = "category", target = "category")
    Book toEntity(RequestCreateBookDto dto);

    /**
     * ResponseAladinSearchDetail을 Book 엔티티로 변환합니다.
     *
     * @param dto ResponseAladinSearchDetail 객체
     * @return 변환된 Book 엔티티
     */
    @Mappings({
        @Mapping(source = "coverImgUrl", target = "coverImgUrl"),
        @Mapping(source = "author", target = "author"),
        @Mapping(source = "publisher", target = "publisher"),
        @Mapping(source = "summary", target = "summary"),
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "isbn", target = "isbn"),
        @Mapping(source = "itemPage", target = "itemPage"),
        @Mapping(source = "sizeDepth", target = "sizeDepth"),
        @Mapping(source = "sizeHeight", target = "sizeHeight"),
        @Mapping(source = "sizeWidth", target = "sizeWidth"),
        @Mapping(source = "category", target = "category"),
        @Mapping(source = "publishedAt", target = "publishedAt", qualifiedByName = "asDate")
    })
    Book toEntity(ResponseAladinSearchDetail dto);

    /**
     * Book 엔티티를 ResponseBookDto로 변환합니다.
     *
     * @param book Book 엔티티
     * @return 변환된 ResponseBookDto
     */
    @Mapping(source = "category", target = "category")
    ResponseBookDto toResponseDto(Book book);

    /**
     * OriginAladinBookItem을 AladinBookItem으로 변환합니다.
     *
     * @param dto OriginAladinBookItem 객체
     * @return 변환된 AladinBookItem
     */
    @Mappings({
        @Mapping(source = "cover", target = "coverImgUrl"),
        @Mapping(source = "author", target = "author"),
        @Mapping(source = "publisher", target = "publisher"),
        @Mapping(source = "description", target = "summary"),
        @Mapping(source = "pubDate", target = "publishedAt", qualifiedByName = "asDate"),
        @Mapping(source = "title", target = "title"),
        @Mapping(source = "isbn", target = "isbn"),
        @Mapping(target = "inLibrary", ignore = true),
        @Mapping(target = "category", ignore = true),
    })
    AladinBookItem toAladinBookItem(OriginAladinBookItem dto);

    /**
     * ResponseOriginAladinAPI를 ResponseAladinAPI로 변환합니다.
     *
     * @param dto ResponseOriginAladinAPI 객체
     * @return 변환된 ResponseAladinAPI
     */
    @Mappings({
        @Mapping(source = "totalResults", target = "totalResults"),
        @Mapping(source = "startIndex", target = "startIndex"),
        @Mapping(source = "item", target = "item"),
        @Mapping(source = "itemsPerPage", target = "itemsPerPage")
    })
    ResponseAladinAPI toResponseAladinAPI(ResponseOriginAladinAPI dto);

    /**
     * ResponseOriginAladinSearchDetail을 ResponseAladinSearchDetail로 변환합니다.
     *
     * @param dto ResponseOriginAladinSearchDetail 객체
     * @return 변환된 ResponseAladinSearchDetail
     */
    @Mapping(source = "cover", target = "coverImgUrl")
    @Mapping(source = "pubDate", target = "publishedAt", qualifiedByName = "asDate")
    @Mapping(source = "description", target = "summary")
    @Mapping(target = "category", ignore = true)
    @Mapping(source = "subInfo.itemPage", target = "itemPage")
    @Mapping(source = "subInfo.packing.sizeDepth", target = "sizeDepth")
    @Mapping(source = "subInfo.packing.sizeHeight", target = "sizeHeight")
    @Mapping(source = "subInfo.packing.sizeWidth", target = "sizeWidth")
    ResponseAladinSearchDetail toResponseSearchDetail(ResponseOriginAladinSearchDetail dto);

    /**
     * ResponseOriginAladinDetail을 ResponseAladinDetail로 변환합니다.
     *
     * @param dto ResponseOriginAladinDetail 객체
     * @return 변환된 ResponseAladinDetail
     */
    ResponseAladinDetail toResopnseAladinDetail(ResponseOriginAladinDetail dto);

    /**
     * Book 엔티티 리스트를 ResponseBookDto 리스트로 변환합니다.
     *
     * @param books Book 엔티티 리스트
     * @return 변환된 ResponseBookDto 리스트
     */
    List<ResponseBookDto> toResponseDtoList(List<Book> books);
}
