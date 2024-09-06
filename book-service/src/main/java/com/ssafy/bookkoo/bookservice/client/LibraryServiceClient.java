package com.ssafy.bookkoo.bookservice.client;

import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OpenFeign 을 이용하여 library service 와 통신
 */
@FeignClient(name = "library-service")
public interface LibraryServiceClient {

    /**
     * bookId 리스트를 주고 해당 책이 내 서재에 존재하는지 여부 확인
     *
     * @param memberId member Id
     * @param bookIds  book Id
     * @return Map<bookId, boolean>
     */
    @GetMapping("/libraries/books/check")
    Map<Long, Boolean> areBooksInLibrary(
        @RequestParam("memberId") Long memberId,
        @RequestParam("bookIds") List<Long> bookIds
    );
}
