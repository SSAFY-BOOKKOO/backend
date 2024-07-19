package com.ssafy.bookkoo.memberservice.entity;

import com.ssafy.bookkoo.memberservice.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_social")
    private Boolean isSocial;

    @Column(name = "categories")
    private String categories;

    @Builder
    public Member(Long id, UUID memberId, String email, String password, Boolean isSocial, String categories) {
        this.id = id;
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.isSocial = isSocial;
        this.categories = categories;
    }
}
