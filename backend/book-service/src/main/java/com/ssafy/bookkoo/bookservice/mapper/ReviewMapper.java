package com.ssafy.bookkoo.bookservice.mapper;

import com.ssafy.bookkoo.bookservice.dto.review.RequestReviewDto;
import com.ssafy.bookkoo.bookservice.dto.review.ResponseReviewDto;
import com.ssafy.bookkoo.bookservice.entity.Review;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    // DTO -> Entity 변환
    Review toEntity(RequestReviewDto dto);

    // Entity -> DTO 변환
    ResponseReviewDto toDto(Review review);

    // Entity -> DTO 리스트 변환
    List<ResponseReviewDto> toDto(List<Review> reviews);
}
