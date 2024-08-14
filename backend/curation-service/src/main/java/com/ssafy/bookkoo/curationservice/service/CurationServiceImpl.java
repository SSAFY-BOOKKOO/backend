package com.ssafy.bookkoo.curationservice.service;

import com.ssafy.bookkoo.curationservice.client.BookServiceClient;
import com.ssafy.bookkoo.curationservice.client.CommonServiceClient;
import com.ssafy.bookkoo.curationservice.client.MemberServiceClient;
import com.ssafy.bookkoo.curationservice.client.NotificationServiceClient;
import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationNotificationDto;
import com.ssafy.bookkoo.curationservice.dto.RequestSendEmailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationListDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseRecipientDto;
import com.ssafy.bookkoo.curationservice.entity.Curation;
import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import com.ssafy.bookkoo.curationservice.exception.BookNotFoundException;
import com.ssafy.bookkoo.curationservice.exception.CurationNotFoundException;
import com.ssafy.bookkoo.curationservice.repository.CurationRepository;
import com.ssafy.bookkoo.curationservice.repository.CurationSendRepository;
import feign.FeignException.FeignClientException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Curation Service
 *
 * @author dino9881
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    final CurationRepository curationRepository;
    final CurationSendRepository curationSendRepository;
    final BookServiceClient bookServiceClient;
    final MemberServiceClient memberServiceClient;
    final NotificationServiceClient notificationServiceClient;
    final CommonServiceClient commonServiceClient;

    /**
     * Curation을 생성하고 전송하는 메서드 생성시 팔로워와 랜덤 멤버 3명에게 전송한다.
     *
     * @param writer            Curation을 작성한 작성자
     * @param createCurationDto Curation 생성 DTO 책 ISBN, Curation 제목, 내용
     */
    @Transactional
    @Override
    public void createCuration(Long writer, RequestCreateCurationDto createCurationDto) {
        try {
            bookServiceClient.getBook(createCurationDto.bookId());
        } catch (FeignClientException exception) {
            throw new BookNotFoundException(createCurationDto.bookId());
        }
        Curation curation = Curation.builder()
                                    .writer(writer)
                                    .book(createCurationDto.bookId())
                                    .title(createCurationDto.title())
                                    .content(createCurationDto.content())
                                    .build();
        curationRepository.save(curation);
        List<ResponseRecipientDto> recipients = memberServiceClient.getLetterRecipients(writer);

        List<Long> notificationList = new ArrayList<>();
        List<String> mailList = new ArrayList<>();
        for (ResponseRecipientDto recipient : recipients) {
            CurationSend curationSend =
                CurationSend.builder()
                            .curation(curation)
                            .receiver(recipient.memberId())
                            .build();
            curationSendRepository.save(curationSend);
            if (recipient.isReceiveEmail()) {
                mailList.add(recipient.email());
            }
            notificationList.add(recipient.memberId());
        }
        try {
            // 알림 보내기
            notificationServiceClient.createCurationNotification(
                RequestCreateCurationNotificationDto.builder()
                                                    .memberIds(
                                                        notificationList.toArray(Long[]::new))
                                                    .curationId(curation.getId())
                                                    .writerId(writer)
                                                    .build());
        } catch (FeignClientException exception) {
            log.info("Caught exception: {}", exception.getMessage());
        }
        try {
            // 메일 보내기
            commonServiceClient.sendMail(RequestSendEmailDto.builder()
                                                            .receivers(
                                                                mailList.toArray(String[]::new))
                                                            .subject("새로운 큐레이션이 도착했어요 "
                                                                + createCurationDto.title())
                                                            .content(createCurationDto.content())
                                                            .build());
        } catch (FeignClientException exception) {
            log.info("Caught exception: {}", exception.getMessage());
        }
    }

    /**
     * 큐레이션 상세정보를 받아온다.
     *
     * @param curationId Curation id
     * @param memberId   member id
     * @return Curation 상세정보(String coverImgUrl, String curationTitle, String writer, String
     * content, String createdAt, String bookTitle, String author, String summary)
     */
    @Transactional
    @Override
    public ResponseCurationDetailDto getCurationDetail(Long curationId, Long memberId) {
        Curation curation = curationRepository.findById(curationId)
                                              .orElseThrow(
                                                  () -> new CurationNotFoundException(
                                                      curationId));
        // 작성자 정보 가져오기
        ResponseMemberInfoDto writerInfo = memberServiceClient.getMemberInfoById(
            curation.getWriter());
        // 작성자가 본인이 아닐경우 본인에게 온 메세지가 맞는지 확인
        Boolean isStored = false;
        if (!curation.getWriter()
                     .equals(memberId)) {
            // PassPort 에서 읽은사람 가져오기
            CurationSend curationSend = curationSendRepository.findCurationSendsByCurationAndReceiver(
                                                                  curation, memberId)
                                                              .orElseThrow(
                                                                  // 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
                                                                  () -> new CurationNotFoundException(
                                                                      curationId));
            isStored = curationSend.getIsStored();
            //읽기 처리
            curationSend.read();
        }
        // BookService 에게 book 정보 받아오기 (책 커버 이미지, 제목, 작가, 줄거리)
        try {
            ResponseBookDto book = bookServiceClient.getBook(curation
                .getBook());
            return ResponseCurationDetailDto.builder()
                                            .bookTitle(book.title())
                                            .summary(book.summary())
                                            .coverImgUrl(book.coverImgUrl())
                                            .author(book.author())
                                            .createdAt(curation.getCreatedAt()
                                                               .toString())
                                            .curationTitle(curation
                                                .getTitle())
                                            .content(curation
                                                .getContent())
                                            .writer(writerInfo.nickName())
                                            .isStored(isStored)
                                            .build();
        } catch (FeignClientException exception) {
            throw new BookNotFoundException(curation.getBook());
        }
    }

    /**
     * 내가 받은 큐레이션들을 가져온다.
     *
     * @param receiver 수신자 Id
     * @return ResponseCurationDto
     */
    @Override
    public ResponseCurationListDto getCurationList(Long receiver, Pageable pageable) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.findCurationSendsByReceiverOrderByCreatedAtDesc(
            receiver, pageable);
        List<Curation> curationList = curationSendByReceiver.stream()
                                                            .map(
                                                                CurationSend::getCuration)
                                                            .toList();
        return ResponseCurationListDto.builder()
                                      .curationList(curationToDto(curationList))
                                      .count(curationSendRepository.countByReceiver(receiver))
                                      .build();
    }

    /**
     * Curation 를 저장한다.
     *
     * @param id : Curation ID
     */
    @Transactional
    @Override
    public void changeCurationStoredStatus(Long id, Long receiver) {
        Curation curation = curationRepository.findById(id)
                                              .orElseThrow(
                                                  () -> new CurationNotFoundException(
                                                      id));
        CurationSend curationSend = curationSendRepository.findCurationSendsByCurationAndReceiver(
                                                              curation, receiver)
                                                          .orElseThrow(
                                                              // 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
                                                              () -> new CurationNotFoundException(
                                                                  id));
        curationSend.changeStoredStatus();
    }

    /**
     * 큐레이션을 삭제한다.
     *
     * @param id       Curation ID
     * @param receiver 삭제할 요청자
     */
    @Override
    @Transactional
    public void deleteCuration(Long id, Long receiver) {
        Curation curation = curationRepository.findById(id)
                                              .orElseThrow(
                                                  () -> new CurationNotFoundException(
                                                      id));
        CurationSend curationSend = curationSendRepository.findCurationSendsByCurationAndReceiver(
                                                              curation, receiver)
                                                          .orElseThrow(
                                                              // 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
                                                              () -> new CurationNotFoundException(
                                                                  id));
        curationSendRepository.deleteById(curationSend.getId());
    }

    /**
     * 내가 작성한 큐레이션리스트를 가져온다.
     *
     * @param writer 작성자 ID
     * @return ResponseCurationDto
     */
    @Override
    public ResponseCurationListDto getSentCurations(Long writer, Pageable pageable) {
        List<Curation> curationList = curationRepository.findCurationsByWriterOrderByCreatedAtDesc(
            writer, pageable);

        return ResponseCurationListDto.builder()
                                      .curationList(curationToDto(curationList))
                                      .count(curationRepository.countByWriter(writer))
                                      .build();
    }

    /**
     * @param receiver : 수신한 사람 (Passport)
     * @return 수신한 큐레이션 DTO (작성자 닉네임, 큐레이션 ID, 큐레이션 제목, 책 커버 이미지)
     */
    @Override
    public ResponseCurationListDto getStoredCurationList(Long receiver, Pageable pageable) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.findCurationSendsByIsStoredAndReceiverOrderByCreatedAtDesc(
            true,
            receiver, pageable);
        List<Curation> curationList = curationSendByReceiver.stream()
                                                            .map(
                                                                CurationSend::getCuration)
                                                            .toList();
        return ResponseCurationListDto.builder()
                                      .curationList(curationToDto(curationList))
                                      .count(
                                          curationSendRepository.countByReceiverAndIsStoredIsTrue(
                                              receiver))
                                      .build();
    }

    /**
     * 큐레이션 리스트 -> DTO 변환 메서드 (private)
     *
     * @param curationList : 변환할 CurationList
     * @return 변환된 CurationDTO List (작성자 닉네임, 큐레이션 ID, 큐레이션 제목, 책 커버 이미지)
     */
    private List<ResponseCurationDto> curationToDto(List<Curation> curationList) {
        List<ResponseCurationDto> responseCurationDtoList = new ArrayList<>();
        for (Curation curation : curationList) {
            ResponseMemberInfoDto writerInfo = memberServiceClient.getMemberInfoById(
                curation.getWriter());
            // BookService 에게 book 정보 받아오기 (책 커버 이미지, 작가)
            try {
                ResponseBookDto book = bookServiceClient.getBook(curation
                    .getBook());
                responseCurationDtoList.add(ResponseCurationDto.builder()
                                                               .writer(writerInfo.nickName())
                                                               .curationId(curation.getId())
                                                               .title(curation.getTitle())
                                                               .coverImgUrl(
                                                                   book.coverImgUrl())
                                                               .build());
            } catch (FeignClientException exception) {
                throw new BookNotFoundException(curation.getBook());
            }
        }
        return responseCurationDtoList;
    }


    /**
     * 매일 오전 4시에  생성된지 15일이 지난 큐레이션들을 삭제하는 메서드
     */
    @Scheduled(cron = "* * 4 * * *")
    public void scheduledDeleteCuration() {
        // 지금 시간을 기준으로 15일 전에 만들어졌고, 저장하지 않은 큐레이션을 삭제한다.
        LocalDateTime time = LocalDateTime.now()
                                          .minusDays(15L);
        curationSendRepository.deleteCurationSendByIsStoredFalseAndCreatedAtBefore(time);
    }
}
