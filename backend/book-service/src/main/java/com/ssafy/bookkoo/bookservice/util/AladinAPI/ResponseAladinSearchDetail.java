package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAladinSearchDetail {

    private String author;
    private String isbn;
    private String description;
    private String title;
    private String pubDate;
    private String coverImgUrl;
    private String publisher;
    private Integer categoryId;

    private AladinSubInfo subInfo;
}
