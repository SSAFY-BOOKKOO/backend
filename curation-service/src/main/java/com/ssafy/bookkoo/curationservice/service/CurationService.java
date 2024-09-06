package com.ssafy.bookkoo.curationservice.service;

import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationListDto;
import org.springframework.data.domain.Pageable;

public interface CurationService {

    // 큐레이션 생성 및 전송
    void createCuration(Long writer, RequestCreateCurationDto requestCreateCurationDto);

    // 큐레이션 디테일 가져오기 (읽기 처리 )
    ResponseCurationDetailDto getCurationDetail(Long curationId, Long memberId);

    // 내가 받은 큐레이션 가져오기
    ResponseCurationListDto getCurationList(Long receiver, Pageable pageable);

    // 내가 저장한 큐레이션 가져오기
    ResponseCurationListDto getStoredCurationList(Long receiver, Pageable pageable);

    // 내가 보낸 큐레이션 가져오기
    ResponseCurationListDto getSentCurations(Long writer, Pageable pageable);

    // 큐레이션 보관하기
    void changeCurationStoredStatus(Long id, Long receiver);

    // 큐레이션 삭제 (지정삭제)
    void deleteCuration(Long id, Long receiver);

}
