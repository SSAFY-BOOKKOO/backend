package com.ssafy.bookkoo.curationservice.service;

import com.ssafy.bookkoo.curationservice.dto.RequestChatbotDto;
import java.util.ArrayList;

public interface OpenAiService {

    String getCompletion(ArrayList<RequestChatbotDto> prompt, Long memberId);
}
