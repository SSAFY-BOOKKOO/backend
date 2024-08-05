package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.repository.BookTalkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTalkServiceImpl implements BookTalkService {

    private final BookTalkRepository bookTalkRepository;

//    @Override
//    public void createBookTalk(RequestCreateBookTalkDto requestCreateBookTalkDto) {
//
//    }

    @Override
    public void enterBookTalk(Long bookTalkId) {

    }
}
