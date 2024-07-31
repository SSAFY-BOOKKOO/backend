package com.ssafy.bookkoo.bookservice.util.AladinAPI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseOriginAladinDetail {

    private List<ResponseOriginAladinSearchDetail> item;
}
