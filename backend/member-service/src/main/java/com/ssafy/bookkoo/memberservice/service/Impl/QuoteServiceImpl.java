package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDetailDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDto;
import com.ssafy.bookkoo.memberservice.entity.Quote;
import com.ssafy.bookkoo.memberservice.exception.QuoteNotFoundException;
import com.ssafy.bookkoo.memberservice.mapper.QuoteMapper;
import com.ssafy.bookkoo.memberservice.repository.QuoteRepository;
import com.ssafy.bookkoo.memberservice.service.QuoteService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    /**
     * 한줄평 저장
     *
     * @param memberId
     * @param createQuoteDto
     * @param backgroundImg
     */
    @Override
    @Transactional
    public void saveQuote(Long memberId, RequestCreateQuoteDto createQuoteDto,
        MultipartFile backgroundImg) {
        Quote quote = quoteMapper.toEntity(createQuoteDto);
        //TODO: 이미지 저장 로직 추가
        quoteRepository.save(quote);
    }

    /**
     * quoteID를 통해 글귀를 삭제합니다.
     *
     * @param memberId
     * @param quoteId
     */
    @Override
    @Transactional
    public void deleteQuote(Long memberId, Long quoteId) {
        quoteRepository.findById(quoteId);
    }

    /**
     * 글귀를 수정합니다.
     *
     * @param memberId
     * @param updateQuoteDto
     * @param backgroundImg
     */
    @Override
    @Transactional
    public void updateQuote(Long memberId, RequestUpdateQuoteDto updateQuoteDto,
        MultipartFile backgroundImg) {
        Quote quote = quoteRepository.findById(updateQuoteDto.quoteId())
                                     .orElseThrow(QuoteNotFoundException::new);
        quote.setContent(updateQuoteDto.content());
        quote.setSource(updateQuoteDto.source());
        //TODO: 이미지 저장 로직 추가
        quoteRepository.flush();
    }

    /**
     * 멤버의 글귀 목록을 페이징을 통해 반환합니다.
     *
     * @param memberId
     * @param pageable
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResponseQuoteDto> getQuotes(Long memberId, Pageable pageable) {
        List<Quote> quotes = quoteRepository.findByMemberId(memberId, pageable);
        return quotes.stream()
                     .map((quoteMapper::toDto))
                     .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseQuoteDetailDto getQuoteDetail(Long memberId, Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                                     .orElseThrow(QuoteNotFoundException::new);
        return quoteMapper.toDetailDto(quote);
    }
}
