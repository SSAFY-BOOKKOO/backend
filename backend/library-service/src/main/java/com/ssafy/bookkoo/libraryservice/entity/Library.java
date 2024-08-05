package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private Long memberId;

    @Column(nullable = false)
    @Setter
    private Integer libraryOrder;

    @OneToOne(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Setter
    private LibraryStyle libraryStyle;

    @OneToMany(mappedBy = "library", cascade = CascadeType.REMOVE)
    private List<LibraryBookMapper> books = new ArrayList<>();

    @Builder
    public Library(
        Long id,
        String name,
        Long memberId,
        Integer libraryOrder
    ) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.libraryOrder = libraryOrder;
    }

    public Integer getBookCount() {
        return books.size();
    }
}
