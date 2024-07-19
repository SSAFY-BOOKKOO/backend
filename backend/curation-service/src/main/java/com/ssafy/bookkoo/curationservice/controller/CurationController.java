package com.ssafy.bookkoo.curationservice.controller;

import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.exception.DtoValidationException;
import com.ssafy.bookkoo.curationservice.service.CurationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curation")
@RequiredArgsConstructor
public class CurationController {

    final CurationService curationService;

    @PostMapping()
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

}
