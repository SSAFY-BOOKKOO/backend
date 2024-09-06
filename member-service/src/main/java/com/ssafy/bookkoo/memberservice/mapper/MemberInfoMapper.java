package com.ssafy.bookkoo.memberservice.mapper;

import com.ssafy.bookkoo.memberservice.dto.response.ResponseFindMemberProfileDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberInfoDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseMemberProfileDto;
import com.ssafy.bookkoo.memberservice.entity.FollowShip;
import com.ssafy.bookkoo.memberservice.entity.MemberCategoryMapper;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MemberInfoMapper {


    // 엔티티를 DTO로 변환
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategories")
    @Mapping(source = "year", target = "age", qualifiedByName = "toAge")
    ResponseMemberInfoDto toResponseDto(MemberInfo memberInfo);


    //마이 페이지에서 사용할 DTO로 변환
    @Mapping(source = "memberInfo.nickName", target = "nickName")
    @Mapping(source = "memberInfo.profileImgUrl", target = "profileImgUrl")
    @Mapping(source = "memberInfo.introduction", target = "introduction")
    @Mapping(source = "memberInfo.memberId", target = "memberId")
    @Mapping(source = "memberInfo.categories", target = "categories", qualifiedByName = "mapCategories")
    @Mapping(source = "memberInfo.followers", target = "followerCnt", qualifiedByName = "mapFollowShipCnt")
    @Mapping(source = "memberInfo.followees", target = "followeeCnt", qualifiedByName = "mapFollowShipCnt")
    ResponseMemberProfileDto toResponseProfileDto(String email, MemberInfo memberInfo);

    //닉네임 검색에 대한 반환 DTO
    @Mapping(source = "memberInfo.nickName", target = "nickName")
    @Mapping(source = "memberInfo.profileImgUrl", target = "profileImgUrl")
    @Mapping(source = "memberInfo.introduction", target = "introduction")
    @Mapping(source = "memberInfo.memberId", target = "memberId")
    @Mapping(source = "memberInfo.categories", target = "categories", qualifiedByName = "mapCategories")
    @Mapping(source = "memberInfo.followers", target = "followerCnt", qualifiedByName = "mapFollowShipCnt")
    @Mapping(source = "memberInfo.followees", target = "followeeCnt", qualifiedByName = "mapFollowShipCnt")
    ResponseFindMemberProfileDto toResponseFindProfileDto(String email, Boolean isFollow, MemberInfo memberInfo);

    @Named("mapCategories")
    default List<Integer> mapCategories(List<MemberCategoryMapper> categories) {
        return categories.stream()
                         .map(category -> category.getMemberCategoryMapperKey()
                                                  .getCategoryId())
                         .collect(Collectors.toList());
    }

    /**
     * 저장된 탄생년도를 통해 나이로 변환해서 매핑
     *
     * @param year
     * @return
     */
    @Named("toAge")
    default int calcAge(Integer year) {
        if (year == null) {
            return 20;
        }
        int currentYear = LocalDate.now()
                                   .getYear();
        return currentYear - year + 1;
    }

    /**
     * 팔로워 카운팅
     *
     * @param followShips
     * @return
     */
    @Named("mapFollowShipCnt")
    default Integer mapFollowShipCnt(List<FollowShip> followShips) {
        return followShips.size();
    }


}
