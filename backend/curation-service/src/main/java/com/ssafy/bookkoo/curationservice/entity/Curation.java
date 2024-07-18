package com.ssafy.bookkoo.curationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Curation 담당 Entity
 *
 * @author dino9881
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long writer;

    @Column(length = 20)
    private String title;

    @Column(nullable = false)
    private Long book;

    @Column(length = 500)
    private String content;

    //TODO createdAt


    @Builder
    public Curation(Long writer, String title, Long book, String content) {
        this.writer = writer;
        this.title = title;
        this.book = book;
        this.content = content;
    }
}
