package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "year")
    private Integer year;

    //TODO: 카테고리 목록 후에 추가
    //@Column(name = "categories")
    //private Long categories;

    @Column(name = "introduction")
    private String introduction;

    @Builder
    public MemberInfo(Long id, String memberId, String nickName, Integer year, String categories,
        String introduction) {
        this.id = id;
        this.memberId = memberId;
        this.nickName = nickName;
        this.year = year;
//        this.categories = categories;
        this.introduction = introduction;
    }
}
