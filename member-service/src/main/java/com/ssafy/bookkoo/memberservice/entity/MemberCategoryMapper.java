package com.ssafy.bookkoo.memberservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCategoryMapper {

    @EmbeddedId
    private MemberCategoryMapperKey memberCategoryMapperKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberInfoId")
    @JoinColumn(name = "member_info_id", nullable = false)
    private MemberInfo memberInfo;


    @Builder
    public MemberCategoryMapper(MemberCategoryMapperKey memberCategoryMapperKey,
        MemberInfo memberInfo) {
        this.memberCategoryMapperKey = memberCategoryMapperKey;
        this.memberInfo = memberInfo;
    }
}