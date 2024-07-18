package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibraryStyle {

    @Id
    private Long libraryId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "library_id")
    private Library library;

    @Column
    private String libraryColor;

    @Builder
    public LibraryStyle(Library library, String libraryColor) {
        this.library = library;
        this.libraryId = library.getId();
        this.libraryColor = libraryColor;
    }
}
