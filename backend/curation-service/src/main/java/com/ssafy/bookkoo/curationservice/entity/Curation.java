package com.ssafy.bookkoo.curationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


/**
 * Curation 담당 Entity
 * @author dino9881
 */
@Entity
public class Curation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long writer;

    @Column
    private Long book;

    @Column
    private String contents;

}
