package com.ssafy.bookkoo.curationservice.service;

import com.ssafy.bookkoo.curationservice.client.BookServiceClient;
import com.ssafy.bookkoo.curationservice.client.MemberServiceClient;
import com.ssafy.bookkoo.curationservice.dto.RequestCreateCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseBookDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDetailDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseCurationDto;
import com.ssafy.bookkoo.curationservice.dto.ResponseMemberInfoDto;
import com.ssafy.bookkoo.curationservice.entity.Curation;
import com.ssafy.bookkoo.curationservice.entity.CurationSend;
import com.ssafy.bookkoo.curationservice.exception.CurationNotFoundException;
import com.ssafy.bookkoo.curationservice.repository.CurationRepository;
import com.ssafy.bookkoo.curationservice.repository.CurationSendRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Curation Service
 *
 * @author dino9881
 */
@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    final CurationRepository curationRepository;
    final CurationSendRepository curationSendRepository;
    final BookServiceClient bookServiceClient;
    final MemberServiceClient memberServiceClient;

    /**
     * Curation을 생성하고 전송하는 메서드 생성시 팔로워와 랜덤 멤버 3명에게 전송한다.
     *
     * @param writer            Curation을 작성한 작성자
     * @param createCurationDto Curation 생성 DTO 책 ISBN, Curation 제목, 내용
     */
    @Transactional
    @Override
    public void createCuration(Long writer, RequestCreateCurationDto createCurationDto) {
        Curation curation = Curation.builder()
                                    .writer(writer)
                                    .book(createCurationDto.bookId())
                                    .title(createCurationDto.title())
                                    .content(createCurationDto.content())
                                    .build();
        curationRepository.save(curation);
        System.out.println(
            memberServiceClient.getMemberInfo("1d5e49b7-e4f5-4953-910f-84376c53325c"));
        //TODO 멤버 정보 받아오기 (돌면서 curation Send 생성)
        for (long i = 2L; i <= 4L; i++) {
            CurationSend curationSend =
                CurationSend.builder()
                            .curation(curation)
                            .receiver(i)
                            .build();
            curationSendRepository.save(curationSend);
        }
    }

    /**
     * 큐레이션 상세정보를 받아온다.
     *
     * @param id CurationSend id
     * @return Curation 상세정보(String coverImgUrl, String curationTitle, String writer, String
     * content, String createdAt, String bookTitle, String author, String summary)
     */
    @Transactional
    @Override
    public ResponseCurationDetailDto getCurationDetail(Long id) {
        Curation curation = curationRepository.findById(id)
                                              .orElseThrow(
                                                  () -> new CurationNotFoundException(
                                                      id));
        //TODO PassPort 에서 읽은사람 가져오기
        CurationSend curationSend = curationSendRepository.findCurationSendsByCurationAndReceiver(
                                                              curation, 2L)
                                                          .orElseThrow(
                                                              //TODO 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
                                                              () -> new CurationNotFoundException(
                                                                  id));
        //읽기 처리
        curationSend.read();
        //TODO MemberService 에게 member 정보 받아오기 (작성자 닉네임)
//        feignMemberService.getMemberInfo(curationSend.getCuration()
//                                                     .getWriter());
        ResponseMemberInfoDto writerInfo = memberServiceClient.getMemberInfo(
            "1d5e49b7-e4f5-4953-910f-84376c53325c");
        // BookService 에게 book 정보 받아오기 (책 커버 이미지, 제목, 작가, 줄거리)
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
                                        .build();

    }

    /**
     * 내가 받은 큐레이션들을 가져온다.
     *
     * @param receiver 수신자 Id
     * @return ResponseCurationDto
     */
    @Override
    public List<ResponseCurationDto> getCurationList(Long receiver) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.findCurationSendsByIsStoredAndReceiver(
            false,
            receiver);
        List<Curation> curationList = curationSendByReceiver.stream()
                                                            .map(
                                                                CurationSend::getCuration)
                                                            .toList();
        return curationToDto(curationList);
    }

    /**
     * Curation 를 저장한다.
     *
     * @param id : Curation ID
     */
    @Transactional
    @Override
    public void storeCuration(Long id, Long receiver) {
        Curation curation = curationRepository.findById(id)
                                              .orElseThrow(
                                                  () -> new CurationNotFoundException(
                                                      id));
        CurationSend curationSend = curationSendRepository.findCurationSendsByCurationAndReceiver(
                                                              curation, receiver)
                                                          .orElseThrow(
                                                              //TODO 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
                                                              () -> new CurationNotFoundException(
                                                                  id));
        curationSend.store();
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
                                                              //TODO 자신이 받은 큐레이션이 아닐경우 권한 Exception 던져야함
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
    public List<ResponseCurationDto> getSentCurations(Long writer) {
        List<Curation> curationList = curationRepository.findCurationsByWriter(writer);

        return curationToDto(curationList);
    }

    /**
     * @param receiver : 수신한 사람 (Passport)
     * @return 수신한 큐레이션 DTO (작성자 닉네임, 큐레이션 ID, 큐레이션 제목, 책 커버 이미지)
     */
    @Override
    public List<ResponseCurationDto> getStoredCurationList(Long receiver) {
        List<CurationSend> curationSendByReceiver = curationSendRepository.findCurationSendsByIsStoredAndReceiver(
            true,
            receiver);
        List<Curation> curationList = curationSendByReceiver.stream()
                                                            .map(
                                                                CurationSend::getCuration)
                                                            .toList();
        return curationToDto(curationList);
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
            //TODO MemberService 에게 member 정보 받아오기 (작성자 닉네임)
            ResponseMemberInfoDto writerInfo = memberServiceClient.getMemberInfo(
                "1d5e49b7-e4f5-4953-910f-84376c53325c");
            // BookService 에게 book 정보 받아오기 (책 커버 이미지, 작가)
            ResponseBookDto book = bookServiceClient.getBook(curation
                .getBook());
            responseCurationDtoList.add(ResponseCurationDto.builder()
                                                           .writer(writerInfo.nickName())
                                                           .curationId(curation.getId())
                                                           .title(curation.getTitle())
                                                           .coverImgUrl(
                                                               book.coverImgUrl())
                                                           .build());
        }
        return responseCurationDtoList;
    }
}
