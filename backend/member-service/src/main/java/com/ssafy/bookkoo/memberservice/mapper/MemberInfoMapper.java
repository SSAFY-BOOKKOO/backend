package com.ssafy.bookkoo.memberservice.mapper;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapper;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MemberInfoMapper {


    // 엔티티를 DTO로 변환
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategories")
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "profileImgUrl", ignore = true)
    ResponseMemberInfoDto toResponseDto(MemberInfo memberInfo);

    @Named("mapCategories")
    default List<Integer> mapCategories(List<MemberCategoryMapper> categories) {
        return categories.stream()
                         .map(category -> category.getMemberCategoryMapperKey()
                                                  .getCategoryId())
                         .toList();
    }
}
