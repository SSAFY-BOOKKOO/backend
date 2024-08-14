package com.ssafy.bookkoo.memberservice.controller;

import com.ssafy.bookkoo.memberservice.dto.request.RequestCreateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.request.RequestUpdateQuoteDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDetailDto;
import com.ssafy.bookkoo.memberservice.dto.response.ResponseQuoteDto;
import com.ssafy.bookkoo.memberservice.service.Impl.OCRService;
import com.ssafy.bookkoo.memberservice.service.QuoteService;
import com.ssafy.bookkoo.memberservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members/quote")
public class QuoteController {

    private final QuoteService quoteService;
    private final OCRService ocrService;

    @GetMapping
    @Operation(summary = "글귀 목록 반환 API"
        , description = "사용자의 글귀를 페이징을 통해 가져옵니다.")
    public ResponseEntity<List<ResponseQuoteDto>> getQuotes(
        @RequestHeader HttpHeaders headers,
        @PageableDefault
        Pageable pageable
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        List<ResponseQuoteDto> quotes = quoteService.getQuotes(memberId, pageable);
        return ResponseEntity.ok(quotes);
    }

    @GetMapping("/detail/{quoteId}")
    @Operation(summary = "글귀 상세 보기 API",
        description = "글귀에 대한 상세 정보를 제공합니다.")
    public ResponseEntity<ResponseQuoteDetailDto> getQuoteDetail(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "quoteId") Long quoteId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        ResponseQuoteDetailDto quoteDetail = quoteService.getQuoteDetail(memberId, quoteId);
        return ResponseEntity.ok(quoteDetail);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "글귀 생성 API"
        , description = "사용자가 새로운 글귀를 생성합니다.")
    public ResponseEntity<HttpStatus> createQuote(
        @RequestHeader HttpHeaders headers,
        @Valid @RequestPart(value = "quoteDto") RequestCreateQuoteDto createQuoteDto,
        @RequestPart(value = "backgroundImg", required = false) MultipartFile backgroundImg
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        quoteService.saveQuote(memberId, createQuoteDto, backgroundImg);
        return ResponseEntity.ok()
                             .build();
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "글귀 수정 API", description = "글귀를 수정합니다.")
    public ResponseEntity<HttpStatus> updateQuote(
        @RequestHeader HttpHeaders headers,
        @Valid @RequestPart(value = "quoteDto") RequestUpdateQuoteDto updateQuoteDto,
        @RequestPart(value = "backgroundImg", required = false) MultipartFile backgroundImg
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        quoteService.updateQuote(memberId, updateQuoteDto, backgroundImg);
        return ResponseEntity.ok()
                             .build();
    }

    @DeleteMapping("/{quoteId}")
    @Operation(summary = "글귀 삭제 API", description = "글귀를 삭제합니다.")
    public ResponseEntity<HttpStatus> deleteQuote(
        @RequestHeader HttpHeaders headers,
        @PathVariable(value = "quoteId") Long quoteId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        quoteService.deleteQuote(memberId, quoteId);
        return ResponseEntity.noContent()
                             .build();
    }

    @GetMapping("/count")
    @Operation(summary = "멤버의 글귀 개수 반환 API",
        description = "멤버가 작성한 글귀의 개수를 반환하는 API")
    public ResponseEntity<Integer> getMyQuoteCount(
        @RequestHeader HttpHeaders headers
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        Integer count = quoteService.getQuoteCount(memberId);
        return ResponseEntity.ok(count);
    }

    @PostMapping(value = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "OCR 이미지 텍스트 추출 API",
        description = "OCR을 통해 글귀의 텍스트를 가져옵니다.")
    public ResponseEntity<String> processingOCR(
        @RequestParam("image") MultipartFile image
    ) {
        String text = ocrService.extractTextFromImage(image);
        return ResponseEntity.ok(text);
    }
}
