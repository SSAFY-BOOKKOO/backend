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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "email")
    private String email;

    @Setter
    @Column(name = "password")
    private String password;

    @Column(name = "is_social")
    private Boolean isSocial;


    @Builder
    public Member(Long id, String memberId, String email, String password, Boolean isSocial) {
        this.id = id;
        this.memberId = memberId;
        this.email = email;
        this.password = password;
        this.isSocial = isSocial;
    }
}
