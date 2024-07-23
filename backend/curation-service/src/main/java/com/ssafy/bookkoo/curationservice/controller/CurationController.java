package com.ssafy.bookkoo.curationservice.controller;

import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDto;
import com.ssafy.bookkoo.curationservice.exception.DtoValidationException;
import com.ssafy.bookkoo.curationservice.service.CurationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curations")
@RequiredArgsConstructor
public class CurationController {

    final CurationService curationService;

    @GetMapping
    @Operation(summary = "내가 받은 큐레이션 리스트 가져오기", description = "수신한 큐레이션 레터 리스트 가져오기")
    public ResponseEntity<List<ResponseCurationDto>> getCurationList() {
        //TODO Passport 에서 receiver 가져오기
        return ResponseEntity.ok(curationService.getCurationList(2L));

    }

    @GetMapping("/mycuration")
    @Operation(summary = "내가 보낸 큐레이션 리스트 가져오기", description = "발신한 큐레이션 레터 리스트 가져오기")
    public ResponseEntity<List<ResponseCurationDto>> getMyCurationList() {
        //TODO Passport 에서 receiver 가져오기
        return ResponseEntity.ok(curationService.getSentCurations(1L));
    }

    @PostMapping
    @Operation(summary = "큐레이션 보내기", description = "큐레이션 레터를 생성하고 보내기")
    public ResponseEntity<Void> createCuration(@RequestBody
    @Valid RequestCreateCurationDto requestCreateCurationDto, Errors errors) {
        // DTO 오류 검증
        if (errors.hasErrors()) {
            throw new DtoValidationException(errors.getAllErrors()
                                                   .get(0)
                                                   .getDefaultMessage());
        }
        //TODO token 에서 사용자 ID 가져오기
        curationService.createCuration(1L, requestCreateCurationDto);
        return ResponseEntity.ok()
                             .build();
    }

    @GetMapping("/detail/{curationId}")
    @Operation(summary = "큐레이션 상세보기", description = "큐레이션 레터의 상세 정보를 확인하기")
    public ResponseEntity<ResponseCurationDetailDto> getCurationDetail(
        @PathVariable Long curationId) {
        return ResponseEntity.ok(curationService.getCurationDetail(curationId));
    }


}
