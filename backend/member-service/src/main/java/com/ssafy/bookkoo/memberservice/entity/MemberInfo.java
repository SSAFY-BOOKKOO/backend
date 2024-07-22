package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Setter
    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "year")
    private Integer year;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    //TODO: 카테고리 목록 후에 추가
    //@Column(name = "categories")
    //private Long categories;

    @Setter
    @Column(name = "introduction")
    private String introduction;

    @Setter
    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Builder
    public MemberInfo(Long id, String memberId, String nickName, Integer year, Gender gender,
        String introduction, String profileImgUrl) {
        this.id = id;
        this.memberId = memberId;
        this.nickName = nickName;
        this.year = year;
        this.gender = gender;
        this.introduction = introduction;
        this.profileImgUrl = profileImgUrl;
    }

}
