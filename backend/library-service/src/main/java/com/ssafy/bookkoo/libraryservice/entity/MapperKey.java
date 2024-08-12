package com.ssafy.bookkoo.libraryservice.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

/**
 * LibraryBookMapper 복합키 클래스 (Embeddable 방식 사용)
 */
@Data
@Embeddable
public class MapperKey implements Serializable {

    private Long bookId;
    private Long libraryId;

}
