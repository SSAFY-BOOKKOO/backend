package com.ssafy.bookkoo.curationservice.client;

import com.ssafy.bookkoo.curationservice.dto.ResponseRecentFiveBookDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {

    String PASSPORT_HEADER = "Member-Passport";

    @GetMapping("/libraries/books/recent")
    List<ResponseRecentFiveBookDto> getRecentFiveBooks(
        @RequestHeader(PASSPORT_HEADER) Long memberId
    );

}
