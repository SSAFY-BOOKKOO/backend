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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LibraryBookMapper {

    @EmbeddedId
    private MapperKey id;

    @ManyToOne
    @MapsId("libraryId")
    @JoinColumn(name = "library_id")
    private Library library;

    @Column
    Long bookOrder;

    @Column
    String bookColor;

    @Column
    Date startAt;

    @Column
    Date endAt;

    @Column
    @Enumerated(EnumType.STRING)
    Status status;
}
