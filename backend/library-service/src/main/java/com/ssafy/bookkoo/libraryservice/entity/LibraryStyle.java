package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @OneToOne
    private Library library;

    @Column
    private String libraryColor;

    @Builder
    public LibraryStyle(Library library, String libraryColor) {
        this.library = library;
        this.libraryColor = libraryColor;
    }


}
