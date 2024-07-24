package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AladinSubInfo {

    private Integer itemPage;
    private AladinPacking packing;
}
