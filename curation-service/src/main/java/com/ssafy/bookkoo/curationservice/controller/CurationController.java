package com.ssafy.bookkoo.curationservice.controller;

import com.ssafy.bookkoo.curationservice.dto.RequestChatbotDto;
import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationListDto;
import com.ssafy.bookkoo.curationservice.service.CurationService;
import com.ssafy.bookkoo.curationservice.service.OpenAiService;
import com.ssafy.bookkoo.curationservice.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curations")
@Slf4j
@RequiredArgsConstructor
public class CurationController {

    final CurationService curationService;
    final OpenAiService openAiService;

    @GetMapping
    @Operation(summary = "내가 받은 큐레이션 리스트 가져오기", description = "수신한 큐레이션 레터 리스트 가져오기")
    public ResponseEntity<ResponseCurationListDto> getCurationList(
        @RequestHeader HttpHeaders headers,
        @RequestParam(name = "page") int page
    ) {
        // Passport 에서 receiver 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        // Paging
        return ResponseEntity.ok(
            curationService.getCurationList(memberId, PageRequest.of(page, 10)));

    }

    @GetMapping("/store")
    @Operation(summary = "내가 보관한 큐레이션 리스트 가져오기", description = "보관한 큐레이션 레터 리스트 가져오기")
    public ResponseEntity<ResponseCurationListDto> getStoredCurationList(
        @RequestHeader HttpHeaders headers,
        @RequestParam(name = "page") int page
    ) {
        // Passport 에서 receiver 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        // Paging
        return ResponseEntity.ok(
            curationService.getStoredCurationList(memberId, PageRequest.of(page, 10)));

    }

    @GetMapping("/mycuration")
    @Operation(summary = "내가 보낸 큐레이션 리스트 가져오기", description = "발신한 큐레이션 레터 리스트 가져오기")
    public ResponseEntity<ResponseCurationListDto> getMyCurationList(
        @RequestHeader HttpHeaders headers,
        @RequestParam(name = "page") int page
    ) {
        // Passport 에서 receiver 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        // Paging
        return ResponseEntity.ok(
            curationService.getSentCurations(memberId, PageRequest.of(page, 10)));
    }

    @PostMapping("/store/{curationId}")
    @Operation(summary = "큐레이션 보관상태 변경", description = "큐레이션 보관상태를 변경합니다 true -> false, false -> true")
    public ResponseEntity<List<ResponseCurationDto>> changeCurationStoredStatus(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long curationId
    ) {
        // Passport 에서 receiver 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        curationService.changeCurationStoredStatus(curationId, memberId);
        return ResponseEntity.ok()
                             .build();

    }


    @PostMapping
    @Operation(summary = "큐레이션 보내기", description = "큐레이션 레터를 생성하고 보내기")
    public ResponseEntity<Void> createCuration(
        @RequestHeader HttpHeaders headers,
        @RequestBody @Valid RequestCreateCurationDto requestCreateCurationDto
    ) {
        // token 에서 사용자 ID 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        curationService.createCuration(memberId, requestCreateCurationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/detail/{curationId}")
    @Operation(summary = "큐레이션 상세보기", description = "큐레이션 레터의 상세 정보를 확인하기")
    public ResponseEntity<ResponseCurationDetailDto> getCurationDetail(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long curationId
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok(curationService.getCurationDetail(curationId, memberId));
    }

    @DeleteMapping("/{curationId}")
    @Operation(summary = "큐레이션 삭제", description = "큐레이션 레터를 삭제하기")
    public ResponseEntity<Void> deleteCuration(
        @RequestHeader HttpHeaders headers,
        @PathVariable Long curationId
    ) {
        // Passport 에서 정보 가져오기
        Long memberId = CommonUtil.getMemberId(headers);
        curationService.deleteCuration(curationId, memberId);
        return ResponseEntity.ok()
                             .build();
    }

    @PostMapping("/chat")
    @Operation(summary = "챗봇", description = "OpenAPI 를 이용한 챗봇 대화")
    public ResponseEntity<String> chatbot(
        @RequestHeader HttpHeaders headers,
        @RequestBody @Valid ArrayList<RequestChatbotDto> requestChatbotDtoList
    ) {
        Long memberId = CommonUtil.getMemberId(headers);
        return ResponseEntity.ok(openAiService.getCompletion(requestChatbotDtoList, memberId));
    }


}
