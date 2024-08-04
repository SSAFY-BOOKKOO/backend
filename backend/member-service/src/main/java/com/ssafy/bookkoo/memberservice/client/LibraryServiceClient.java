package com.ssafy.bookkoo.memberservice.client;

import com.ssafy.bookkoo.memberservice.client.dto.request.RequestCreateLibraryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "library-service")
public interface LibraryServiceClient {
    String PASSPORT_HEADER = "Member-Passport";

    @PostMapping("/libraries")
    void createLibrary(
        @RequestHeader(PASSPORT_HEADER) Long memberId,
        @RequestBody RequestCreateLibraryDto createLibraryDto
    );
}
