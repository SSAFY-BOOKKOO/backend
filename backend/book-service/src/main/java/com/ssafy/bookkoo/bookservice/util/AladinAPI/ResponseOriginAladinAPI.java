package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOriginAladinAPI {

    private Integer totalResults;
    private Integer startIndex;
    private List<OriginAladinBookItem> item;
    private Integer itemsPerPage;

}
