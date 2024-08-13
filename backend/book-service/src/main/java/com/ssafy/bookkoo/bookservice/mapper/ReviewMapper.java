package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Review 엔티티와 DTO 간의 변환을 처리하는 매퍼 인터페이스입니다.
 */
@Mapper(componentModel = "spring")
public interface ReviewMapper {

    /**
     * RequestReviewDto를 Review 엔티티로 변환합니다.
     *
     * @param dto RequestReviewDto 객체
     * @return 변환된 Review 엔티티
     */
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "memberId", ignore = true)
    Review toEntity(RequestReviewDto dto);

    /**
     * Review 엔티티를 ResponseReviewDto로 변환합니다.
     *
     * @param review Review 엔티티
     * @return 변환된 ResponseReviewDto
     */
    @Mapping(target = "bookId", source = "book.id")
    ResponseReviewDto toDto(Review review);

    /**
     * Review 엔티티 리스트를 ResponseReviewDto 리스트로 변환합니다.
     *
     * @param reviews Review 엔티티 리스트
     * @return 변환된 ResponseReviewDto 리스트
     */
    @Mapping(target = "bookId", source = "book.id")
    List<ResponseReviewDto> toDto(List<Review> reviews);
}
