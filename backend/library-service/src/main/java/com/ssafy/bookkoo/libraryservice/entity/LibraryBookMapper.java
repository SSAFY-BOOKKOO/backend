package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.sql.Date;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibraryBookMapper {

    @EmbeddedId
    private MapperKey id;

    @ManyToOne
    @MapsId("libraryId")
    @JoinColumn(name = "library_id")
    @Setter
    private Library library;

    @Column
    Integer bookOrder;

    @Column
    String bookColor;

    @Column
    Date startAt;

    @Column
    Date endAt;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;

    @Column
    Integer rating;

    @Builder
    public LibraryBookMapper(
        MapperKey id,
        Integer bookOrder,
        String bookColor,
        Date startAt,
        Date endAt,
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
