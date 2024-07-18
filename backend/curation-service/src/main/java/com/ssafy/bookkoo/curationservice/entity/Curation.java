package com.ssafy.bookkoo.curationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Curation 담당 Entity
 *
 * @author dino9881
 */
@Entity
@Getter
@RequiredArgsConstructor
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String title;

    @Column
    private Long writer;

    @Column
    private Long book;

    @Column(length = 500)
    private String contents;

}
