package com.ssafy.bookkoo.notificationservice.client;

import com.ssafy.bookkoo.notificationservice.client.dto.ResponseCurationReceiveDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "curation-service")
public interface CurationServiceClient {

    /**
     * 큐레이션에 대한 간략한 정보를 받는 서비스
     *
     * @return
     */
    //TODO: 요청 API에 적어뒀음 만들어주면 URI바꾸기
    @GetMapping("/detail/{curationId}")
    ResponseCurationReceiveDto getCurationReceive(@PathVariable Long curationId);
}
