package com.ssafy.bookkoo.libraryservice.entity;

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
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String member;

    @Column(nullable = false)
    private Integer libraryOrder;

    @Builder
    public Library(String name, String member, Integer libraryOrder) {
        this.name = name;
        this.member = member;
        this.libraryOrder = libraryOrder;
    }


}
