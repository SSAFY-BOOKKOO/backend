package com.ssafy.bookkoo.libraryservice.entity;

import com.ssafy.bookkoo.libraryservice.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibraryBookMapper extends BaseEntity {

    @EmbeddedId
    private MapperKey id;

    @ManyToOne
    @MapsId("libraryId")
    @JoinColumn(name = "library_id")
    @Setter
    private Library library;

    @Column
    @Setter
    Integer bookOrder;

    @Column
    @Setter
    String bookColor;

    @Column
    @Setter
    LocalDate startAt;

    @Column
    @Setter
    LocalDate endAt;

    @Column
    @Enumerated(EnumType.STRING)
    @Setter
    Status status;

    @Column
    @Setter
    Integer rating;

    @Builder
    public LibraryBookMapper(
        MapperKey id,
        Integer bookOrder,
        String bookColor,
        LocalDate startAt,
        LocalDate endAt,
        Status status,
        Integer rating
    ) {
        this.id = id;
        this.bookOrder = bookOrder;
        this.bookColor = bookColor;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
        this.rating = rating;
    }
}
