package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
