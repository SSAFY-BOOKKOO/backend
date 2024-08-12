package com.ssafy.bookkoo.booktalkservice.client;

import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.booktalkservice.dto.other.RequestSearchBookMultiFieldDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("/books/{bookId}")
    ResponseBookDto getBook(@PathVariable("bookId") Long bookId);

    /**
     * 조건을 기반으로 책 목록을 조회하는 메서드입니다.
     *
     * @param filter 검색 조건 DTO
     * @return 검색된 책 응답 DTO 리스트
     */
    @PostMapping("/books/search")
    List<ResponseBookDto> getBooksByCondition(
        @RequestBody RequestSearchBookMultiFieldDto filter
    );
}
