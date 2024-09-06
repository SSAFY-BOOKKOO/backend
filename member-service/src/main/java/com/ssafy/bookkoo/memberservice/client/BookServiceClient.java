package com.ssafy.bookkoo.memberservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @DeleteMapping("/books/reviews/me")
    void deleteMyReview(@RequestParam("memberId") Long id);
}
