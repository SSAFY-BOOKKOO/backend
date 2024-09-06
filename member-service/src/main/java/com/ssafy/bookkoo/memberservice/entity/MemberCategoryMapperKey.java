package com.ssafy.bookkoo.memberservice.entity;


import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCategoryMapperKey implements Serializable {

    private Long memberInfoId;

    private Integer categoryId;

    @Builder

    public MemberCategoryMapperKey(Long memberInfoId, Integer categoryId) {
        this.memberInfoId = memberInfoId;
        this.categoryId = categoryId;
    }
}
