package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.enums.Gender;
import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @OneToMany(mappedBy = "memberInfo")
    private List<MemberCategoryMapper> categories = new ArrayList<>();

    @Setter
    @Column(name = "introduction")
    private String introduction;

    @Setter
    @Column(name = "profile_img_url")
    private String profileImgUrl;

    //해당 멤버를 팔로우하는 팔로우 관계
    @OneToMany(mappedBy = "followee")
    private List<FollowShip> followers = new ArrayList<>();

    //해당 멤버가 팔로잉하는 팔로우 관계
    @OneToMany(mappedBy = "follower")
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

    /**
     * 연관관계 편의 메서드
     */
    /**
     * 해당 멤버가 팔로우하는 목록에 추가
     *
     * @param followShip
     */
    public void addFollowees(FollowShip followShip, MemberInfo followeeInfo) {
        if (!this.followees.contains(followShip)) {
            this.followees.add(followShip);
            this.followers.add(followShip);
        }
        followShip.setFollowee(followeeInfo);
        followShip.setFollower(this);
    }

    /**
     * 해당 멤버를 팔로우하는 목록에 추가
     *
     * @param followShip
     */
    public void addFollowers(FollowShip followShip, MemberInfo followerInfo) {
        if (!followers.contains(followShip)) {
            this.followees.add(followShip);
            this.followers.add(followShip);
        }
        followShip.setFollower(followerInfo);
        followShip.setFollowee(this);
    }

    /**
     * 해당 멤버를 언팔로우
     *
     * @param followShip
     */
    public void removeFollower(FollowShip followShip) {
        followers.remove(followShip);
    }

    /**
     * 해당 멤버가 언팔로우
     *
     * @param followShip
     */
    public void removeFollowee(FollowShip followShip) {
        followees.remove(followShip);
    }
}
