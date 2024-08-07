package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalkMemberMapper;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkMapperRepository;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTalkServiceImpl implements BookTalkService {

    private final BookTalkRepository bookTalkRepository;
    private final BookTalkMapperRepository bookTalkMapperRepository;

    @Override
    public void createBookTalk(RequestCreateBookTalkDto dto) {
        // TODO 책 정보 받아오기
        // TODO 이미 있는 책이면 exception
        BookTalk bookTalk = BookTalk.builder()
                                    .book(dto.bookId())
                                    .build();
    }

    @Override
    public void enterBookTalk(Long bookTalkId, Long memberId) {
        //TODO 없을때 exception 추가
        BookTalk bookTalk = bookTalkRepository.findById(bookTalkId)
                                              .orElseThrow(RuntimeException::new
                                              );
        BookTalkMemberMapper bookTalkMemberMapper = BookTalkMemberMapper.builder()
                                                                        .memberId(memberId)
                                                                        .booktalkId(bookTalk)
                                                                        .build();
        //TODO 참여 정보가 없으면 저장, 들어요면 message subscribe
        bookTalkMapperRepository.save(bookTalkMemberMapper);
    }

    @Override
    public List<ResponseBookTalkDto> getMyBookTalkList(Long memberId, String order) {
        return List.of();
    }

    @Override
    public List<ResponseBookTalkDto> getPopularBookTalkList(Long memberId) {
        return List.of();
    }

    @Override
    public ResponseBookTalkDto findBookTalk(Long bookId) {
        return null;
    }
}
