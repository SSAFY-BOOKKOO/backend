package com.ssafy.bookkoo.booktalkservice.service;

import com.ssafy.bookkoo.booktalkservice.client.BookServiceClient;
import com.ssafy.bookkoo.booktalkservice.client.NotificationServiceClient;
import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.RequestCreateCommunityNotificationDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.booktalkservice.dto.ResponseBookTalkDto;
import com.ssafy.bookkoo.booktalkservice.dto.other.RequestSearchBookMultiFieldDto;
import com.ssafy.bookkoo.booktalkservice.dto.other.SearchBookConditionDto;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalk;
import com.ssafy.bookkoo.booktalkservice.entity.BookTalkMemberMapper;
import com.ssafy.bookkoo.booktalkservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.booktalkservice.exception.BookTalkAlreadyExistException;
import com.ssafy.bookkoo.booktalkservice.exception.BookTalkNotFoundException;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkMapperRepository;
import com.ssafy.bookkoo.booktalkservice.repository.BookTalkRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class BookTalkServiceImpl implements BookTalkService {

    private final BookTalkRepository bookTalkRepository;
    private final BookTalkMapperRepository bookTalkMapperRepository;
    private final BookServiceClient bookServiceClient;
    private final NotificationServiceClient notificationServiceClient;

    @Override
    public Long createBookTalk(RequestCreateBookTalkDto dto) {
        // 없는 책이라면 Exception
        try {
            bookServiceClient.getBook(dto.bookId());
        } catch (FeignException e) {
            throw new BookNotFoundException(dto.bookId());
        }
        // 이미 있는 책이면 exception
        if (bookTalkRepository.existsByBook(dto.bookId())) {
            throw new BookTalkAlreadyExistException();
        }
        BookTalk bookTalk = BookTalk.builder()
                                    .book(dto.bookId())
                                    .build();
        bookTalkRepository.save(bookTalk);
        return bookTalk.getId();
    }

    @Override
    public void enterBookTalk(Long bookTalkId, Long memberId) {
        // 독서록 정보가 없을때 예외
        BookTalk bookTalk = bookTalkRepository.findById(bookTalkId)
                                              .orElseThrow(BookTalkNotFoundException::new
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
    public List<ResponseBookTalkDto> getMyBookTalkList(
        Long memberId, String order,
        Pageable pageable
    ) {
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
        return bookTalkRepository.findById(bookTalkId)
                                 .orElseThrow(BookTalkNotFoundException::new);
    }

    @Override
    public ResponseBookTalkDto getBookTalkByBookId(Long bookId) {
        BookTalk bookTalk = bookTalkRepository.findByBook(bookId)
                                              .orElseThrow(BookTalkNotFoundException::new);
        return toDto(bookTalk);
    }

    /**
     * 북톡에 있는 책 반환
     *
     * @param searchDto 검색 조건
     * @return List(ResponseBookDto)
     */
    @Override
    public List<ResponseBookDto> searchBookTalkBooks(
        RequestSearchBookMultiFieldDto searchDto
    ) {
        // 라이브러리 ID로 연결된 책 ID 목록을 가져옵니다.
        List<Long> bookIds = bookTalkMapperRepository.findAllBookIds();
        // 없을 경우
        if (bookIds.isEmpty()) {
            return List.of();
        }
        // 책 ID 목록을 String 목록으로 변환합니다.
        List<String> stringBookIds = bookIds.stream()
                                            .map(String::valueOf)
                                            .toList();

        List<SearchBookConditionDto> conditions = new ArrayList<>();
        // BookServiceClient를 통해 책 정보를 가져옵니다.
        // 1. bookId로 condition 생성
        conditions.add(SearchBookConditionDto.builder()
                                             .field("id")
                                             .values(stringBookIds)
                                             .build());
        // 2. 제목/출판사/저자로 필터링하는 condition 생성
        conditions.addAll(searchDto.conditions());

        // 필터 DTO를 생성합니다.
        RequestSearchBookMultiFieldDto filterDto = RequestSearchBookMultiFieldDto.builder()
                                                                                 .conditions(
                                                                                     conditions)
                                                                                 .limit(
                                                                                     searchDto.limit())
                                                                                 .offset(
                                                                                     searchDto.offset())
                                                                                 .build();

        // 책 정보 가져오기
        List<ResponseBookDto> books = bookServiceClient.getBooksByCondition(filterDto);

        return books;
    }

    /**
     * 독서록의 정보를 DTO 로 반환하는 메서드
     *
     * @param bookTalk : 독서록 번호
     * @return 독서록 정보 DTO
     */
    private ResponseBookTalkDto toDto(BookTalk bookTalk) {
        try {
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
        } catch (FeignException e) {
            throw new BookNotFoundException();
        }
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void dayCountDelete() {
        log.info("{} : dayCountDelete", LocalDateTime.now());
        bookTalkRepository.dayCountDelete();
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledBookTalkDeleteNotification() {

        LocalDateTime notificationTime = LocalDateTime.now()
                                                      .minusDays(14);
        // 14일 된 독서록들에게는 알림을 보내야함
        List<BookTalk> notiBookTalkList = bookTalkRepository.findByUpdatedAtBefore(
            notificationTime);
        notiBookTalkList.forEach(bookTalk -> {
            // 해당 독서록의 책 이름
            String bookTitle = null;
            try {
                bookTitle = bookServiceClient.getBook(bookTalk.getBook())
                                             .title();
            } catch (FeignException e) {
                log.info("BookTalk : bookServiceClient Error bookId = {}", bookTalk.getBook());
                // 책 제목을 가져오지 못했을 경우 알림을 생성하지 않는다.
                return;
            }
            // 해당 독서록에 있는 회원 번호 가져오기
            List<BookTalkMemberMapper> mappers = bookTalkMapperRepository.findByBooktalk(
                bookTalk);
            List<Long> members = mappers.stream()
                                        .map(BookTalkMemberMapper::getMemberId)
                                        .toList();
            // 알림 생성 요청
            try {
                notificationServiceClient.createCommunityNotification(
                    RequestCreateCommunityNotificationDto.builder()
                                                         .memberIds(
                                                             members.toArray(new Long[0]))
                                                         .title(bookTitle)
                                                         .communityId(bookTalk.getId())
                                                         .build());
            } catch (FeignException e) {
                log.info("BookTalk : notificationServiceClient Error bookTalkId = {}",
                    bookTalk.getId());
            }
        });
    }

    @Scheduled(cron = "0 0 4 * * *")
    @Transactional
    public void scheduledBookTalkDelete() {
        LocalDateTime deleteTime = LocalDateTime.now()
                                                .minusDays(15);
        List<BookTalk> deleteBookTalkList = bookTalkRepository.findByUpdatedAtBefore(
            deleteTime);
        bookTalkRepository.deleteAll(deleteBookTalkList);
    }

}
