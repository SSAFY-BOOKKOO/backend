package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.client.BookServiceClient;
import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalkMemberMapper;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkMapperRepository;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookTalkServiceImpl implements BookTalkService {

    private final BookTalkRepository bookTalkRepository;
    private final BookTalkMapperRepository bookTalkMapperRepository;
    private final BookServiceClient bookServiceClient;

    @Override
    public Long createBookTalk(RequestCreateBookTalkDto dto) {
        // TODO 없는 책이라면 Exception
        // 이미 있는 책이면 exception
        // TODO Exception 생성
        if (bookTalkRepository.existsByBook(dto.bookId())) {
            throw new RuntimeException();
        }
        BookTalk bookTalk = BookTalk.builder()
                                    .book(dto.bookId())
                                    .build();
        bookTalkRepository.save(bookTalk);
        return bookTalk.getId();
    }

    @Override
    public void enterBookTalk(Long bookTalkId, Long memberId) {
        //TODO 독서록 정보가 없을때 (bookTalkId 가 잘못되었을때) exception 추가
        BookTalk bookTalk = bookTalkRepository.findById(bookTalkId)
                                              .orElseThrow(RuntimeException::new
                                              );
        BookTalkMemberMapper bookTalkMemberMapper = BookTalkMemberMapper.builder()
                                                                        .memberId(memberId)
                                                                        .booktalk(bookTalk)
                                                                        .build();
        //참여 정보가 없으면 저장 (내가 참여한 북톡 정보를 얻기 위해)
        if (!bookTalkMapperRepository.existsByMemberIdAndBooktalk(memberId, bookTalk)) {
            bookTalkMapperRepository.save(bookTalkMemberMapper);
        }
    }

    @Override
    public List<ResponseBookTalkDto> getMyBookTalkList(Long memberId, String order,
        Pageable pageable) {
        List<ResponseBookTalkDto> bookTalkDtos = new ArrayList<>();
        // 내가 참여한 독서록 매퍼
        List<BookTalkMemberMapper> mappers = bookTalkMapperRepository.findByMemberId(memberId);
        // 참여한 독서록의 ID 를 가져온다.
        List<Long> booktalkIds = mappers.stream()
                                        .map(BookTalkMemberMapper::getBooktalk)
                                        .toList()
                                        .stream()
                                        .map(BookTalk::getId)
                                        .toList();
        List<BookTalk> bookTalkList = new ArrayList<>();
        // 최근 채팅순 정렬
        if (order.equals("time")) {
            bookTalkList = bookTalkRepository.findByIdInOrderByUpdatedAtDesc(booktalkIds, pageable);

        }
        // 채팅 많은 순 정렬
        else if (order.equals("chat")) {
            bookTalkList = bookTalkRepository.findByIdInOrderByTotalMessageCountDesc(booktalkIds,
                pageable);
        }
        bookTalkList.forEach((bookTalk -> {
            bookTalkDtos.add(toDto(bookTalk));
        }));
        return bookTalkDtos;
    }

    // 인기있는 독서록 리스트
    @Override
    public List<ResponseBookTalkDto> getPopularBookTalkList(Pageable pageable) {
        return bookTalkRepository.findAllByOrderByDayMessageCountDesc(pageable)
                                 .stream()
                                 .map(this::toDto)
                                 .collect(Collectors.toList());
    }

    @Override
    public BookTalk findBookTalk(Long bookTalkId) {
        //TODO Custom Exception 만들기
        return bookTalkRepository.findById(bookTalkId)
                                 .orElseThrow(RuntimeException::new);
    }

    @Override
    public ResponseBookTalkDto getBookTalkByBookId(Long bookId) {
        //TODO Custom Exception 만들기
        BookTalk bookTalk = bookTalkRepository.findByBook(bookId)
                                              .orElseThrow(RuntimeException::new);
        return toDto(bookTalk);
    }

    /**
     * 독서록의 정보를 DTO 로 반환하는 메서드
     *
     * @param bookTalk : 독서록 번호
     * @return 독서록 정보 DTO
     */
    private ResponseBookTalkDto toDto(BookTalk bookTalk) {
        ResponseBookDto bookInfo = bookServiceClient.getBook(bookTalk.getBook());
        ResponseBookTalkDto dto = ResponseBookTalkDto.builder()
                                                     .bookTalkId(bookTalk.getId())
                                                     .chats(bookTalk.getTotalMessageCount())
                                                     .lastChatTime(bookTalk.getUpdatedAt()
                                                                           .toString())
                                                     .title(bookInfo.title())
                                                     .author(bookInfo.author())
                                                     .coverImgUrl(bookInfo.coverImgUrl())
                                                     .category(bookInfo.category()
                                                                       .name())
                                                     .build();
        return dto;
    }


}
