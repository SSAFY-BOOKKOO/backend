package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowShip extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private MemberInfo follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followed_id")
    private MemberInfo followee;
}
