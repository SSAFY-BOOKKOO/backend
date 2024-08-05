package com.ssafy.bookkoo.booktalkservice.controller;

import com.ssafy.bookkoo.booktalkservice.mongo.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookTalkController {

    private final ChatMessageRepository chatMessageRepository;


}
