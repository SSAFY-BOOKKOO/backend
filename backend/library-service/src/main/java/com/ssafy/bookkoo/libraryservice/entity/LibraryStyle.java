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
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibraryStyle {

    @Id
    private Long id; // 실제론 안 쓰인다네요

    @OneToOne
    @MapsId
    @JoinColumn(name = "library_id")
    private Library library;

    @Column
    @Setter
    private String libraryColor;

    @Column
    @Setter
    private String fontName;

    @Column
    @Setter
    private String fontSize;

    @Builder
    public LibraryStyle(
        Library library,
        String libraryColor,
        String fontName,
        String fontSize
    ) {
        this.library = library;
        this.libraryColor = libraryColor;
        this.fontName = fontName;
        this.fontSize = fontSize;
    }
}
