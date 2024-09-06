package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.enums.Gender;
import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @JoinColumn(name = "member")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Member member;

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

    @OneToMany(mappedBy = "memberInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MemberCategoryMapper> categories = new ArrayList<>();

    @Setter
    @Column(name = "introduction")
    private String introduction;

    @Setter
    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @JoinColumn(name = "member_setting")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private MemberSetting memberSetting;

    //해당 멤버를 팔로우하는 팔로우 관계
    @OneToMany(mappedBy = "followee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FollowShip> followers = new ArrayList<>();

    //해당 멤버가 팔로잉하는 팔로우 관계
    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<FollowShip> followees = new ArrayList<>();

    @OneToMany(mappedBy = "memberInfo", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Quote> quotes = new ArrayList<>();

    @Builder
    public MemberInfo(
        Long id,
        Member member,
        String memberId,
        String nickName,
        Integer year,
        Gender gender,
        String introduction,
        String profileImgUrl,
        MemberSetting memberSetting,
        List<Quote> quotes) {
        this.id = id;
        this.member = member;
        this.memberId = memberId;
        this.nickName = nickName;
        this.year = year;
        this.gender = gender;
        this.introduction = introduction;
        this.profileImgUrl = profileImgUrl;
        this.memberSetting = memberSetting;
        this.quotes = quotes;
    }

    /**
     * 연관관계 편의 메서드
     */

    /**
     * 멤버의 선호 카테고리 추가
     */
    public void addCategory(MemberCategoryMapper categoryMapper) {
        this.categories.add(categoryMapper);
    }

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

    /**
     * 글귀 추가
     */
    public void addQuote(Quote quote) {
        this.quotes.add(quote);
        quote.setMemberInfo(this);
    }

    public void removeQuote(Quote quote) {
        this.quotes.remove(quote);
    }
}
