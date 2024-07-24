package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo extends BaseEntity {

    @Id
    @Column(name = "id")
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

    @OneToMany(mappedBy = "follower")
    private List<FollowShip> followers = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<FollowShip> followees = new ArrayList<>();


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
