package com.ssafy.bookkoo.memberservice.service;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDetailDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface QuoteService {

    void saveQuote(Long memberId, RequestCreateQuoteDto createQuoteDto,
        MultipartFile backgroundImg);

    void deleteQuote(Long memberId, Long qouteId);

    void updateQuote(Long memberId, RequestUpdateQuoteDto updateQuoteDto,
        MultipartFile backgroundImg);

    List<ResponseQuoteDto> getQuotes(Long memberId, Pageable pageable);

    ResponseQuoteDetailDto getQuoteDetail(Long memberId, Long quoteId);

    Integer getQuoteCount(Long memberId);
}
