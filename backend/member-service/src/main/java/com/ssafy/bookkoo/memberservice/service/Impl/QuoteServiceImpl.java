package com.ssafy.bookkoo.memberservice.service.Impl;

import com.ssafy.bookkoo.memberservice.client.CommonServiceClient;
import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDetailDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDto;
import com.ssafy.bookkoo.memberservice.entity.MemberInfo;
import com.ssafy.bookkoo.memberservice.entity.Quote;
import com.ssafy.bookkoo.memberservice.exception.ImageUploadException;
import com.ssafy.bookkoo.memberservice.exception.MemberInfoNotExistException;
import com.ssafy.bookkoo.memberservice.exception.QuoteNotFoundException;
import com.ssafy.bookkoo.memberservice.exception.UnAuthorizationException;
import com.ssafy.bookkoo.memberservice.mapper.QuoteMapper;
import com.ssafy.bookkoo.memberservice.repository.MemberInfoRepository;
import com.ssafy.bookkoo.memberservice.repository.QuoteRepository;
import com.ssafy.bookkoo.memberservice.service.MemberInfoService;
import com.ssafy.bookkoo.memberservice.service.QuoteService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final CommonServiceClient commonServiceClient;
    private final MemberInfoService memberInfoService;

    @Value("${config.quote-bucket-name}")
    private String BUCKET;


    @Value("${config.default-quote-img-url}")
    private String DEFAULT_QUOTE_IMG_URL;

    @Value("${config.server-url}")
    private String SERVER;

    @Value("${config.common-service-file}")
    private String COMMON_URL;

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
        MemberInfo memberInfo = getMemberInfo(memberId);
        Quote quote = quoteMapper.toEntity(createQuoteDto);
        quote.setMemberInfo(memberInfo);
        String imgUrl = DEFAULT_QUOTE_IMG_URL;
        if (backgroundImg != null) {
            try {
                imgUrl = commonServiceClient.saveImg(backgroundImg, BUCKET);
            } catch (Exception e) {
                throw new ImageUploadException();
            }
            imgUrl = SERVER + COMMON_URL + imgUrl;
        }
        quote.setBackgroundImgUrl(imgUrl);
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
        Quote quote = quoteRepository.findById(quoteId)
                                     .orElseThrow(QuoteNotFoundException::new);
        Long quoteMemberId = quote.getMemberInfo()
                                  .getId();
        if (!quoteMemberId.equals(memberId)) {
            throw new UnAuthorizationException();
        }
        quoteRepository.delete(quote);
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
        if (backgroundImg != null) {

            String imgUrl = null;
            try {
                imgUrl = commonServiceClient.saveImg(backgroundImg, BUCKET);
            } catch (Exception e) {
                throw new ImageUploadException();
            }
            imgUrl = SERVER + COMMON_URL + imgUrl;
            quote.setBackgroundImgUrl(imgUrl);
        }
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

    /**
     * 자신의 글귀에 대한 상세 정보를 반환 (본인 것 아니면 예외 발생)
     *
     * @param memberId
     * @param quoteId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseQuoteDetailDto getQuoteDetail(Long memberId, Long quoteId) {
        Quote quote = quoteRepository.findById(quoteId)
                                     .orElseThrow(QuoteNotFoundException::new);
        Long quoteMemberId = quote.getMemberInfo()
                                  .getId();
        if (!quoteMemberId.equals(memberId)) {
            throw new UnAuthorizationException();
        }
        return quoteMapper.toDetailDto(quote);
    }

    /**
     * 글귀 총 개수를 반환합니다.
     * @param memberId
     * @return
     */
    @Override
    public Integer getQuoteCount(Long memberId) {
        MemberInfo memberInfo = getMemberInfo(memberId);
        return memberInfo.getQuotes()
                         .size();
    }

    /**
     * 멤버 서비스로부터 멤버 정보를 받아옵니다.
     * @param memberId
     * @return
     */
    private MemberInfo getMemberInfo(Long memberId) {
        return memberInfoService.getMemberInfoEntity(memberId);
    }
}
