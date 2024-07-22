package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
    private Long memberId;

    @Column(nullable = false)
    private Integer libraryOrder;

    @OneToOne(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LibraryStyle libraryStyle;

    @Builder
    public Library(
        String name,
        Long memberId,
        Integer libraryOrder
    ) {
        this.name = name;
        this.memberId = memberId;
        this.libraryOrder = libraryOrder;
    }


}
